package asgn2Manifests;

import java.util.ArrayList;
import java.util.List;

import asgn2Codes.ContainerCode;
import asgn2Containers.FreightContainer;
import asgn2Exceptions.ManifestException;

/**
 * A class for managing a container ship's cargo manifest. It allows freight
 * containers of various types to be loaded and unloaded, within various
 * constraints.
 * <p>
 * In particular, the ship's captain has set the following rules for loading of
 * new containers:
 * <ol>
 * <li>
 * New containers may be loaded only if doing so does not exceed the ship's
 * weight limit.</li>
 * <li>
 * New containers are to be loaded as close to the bridge as possible. (Stack
 * number zero is nearest the bridge.)</li>
 * <li>
 * A new container may be added to a stack only if doing so will not exceed the
 * maximum allowed stack height.
 * <li>
 * A new container may be loaded only if a container with the same code is not
 * already on board.</li>
 * <li>
 * Stacks of containers must be homogeneous, i.e., each stack must consist of
 * containers of one type (general, refrigerated or dangerous goods) only.</li>
 * </ol>
 * <p>
 * Furthermore, since the containers are moved by an overhead crane, a container
 * can be unloaded only if it is on top of a stack.
 * 
 * @author Aeron (08667501) 
 * @version 1.0
 */
public class CargoManifest {

    private Integer numStacks;
    private Integer maxHeight;
    private Integer maxWeight;
    private ArrayList<ArrayList<FreightContainer>> manifest;

    private Integer loadedWeight = 0;

    private Integer InitializeStack = 0;
    private Integer loadedContainer = 0;

    /**
     * Constructs a new cargo manifest in preparation for a voyage. When a cargo
     * manifest is constructed the specific cargo parameters for the voyage are
     * set, including the number of stack spaces available on the deck (which
     * depends on the deck configuration for the voyage), the maximum allowed
     * height of any stack (which depends on the weather conditions expected for
     * the voyage), and the total weight of containers allowed onboard (which
     * depends on the amount of ballast and fuel being carried).
     * 
     * @param numStacks
     *            the number of stacks that can be accommodated on deck
     * @param maxHeight
     *            the maximum allowable height of any stack
     * @param maxWeight
     *            the maximum weight of containers allowed on board (in tonnes)
     * @throws ManifestException
     *             if negative numbers are given for any of the parameters
     */
    public CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)
            throws ManifestException {
        loadedContainer = 0;
        if (numStacks < 0 || maxHeight < 0 || maxWeight < 0) {
            throw new ManifestException("Invalid parameters: No Negative Value");
        }

        this.numStacks = numStacks;
        this.maxHeight = maxHeight;
        this.maxWeight = maxWeight;

        manifest = new ArrayList<ArrayList<FreightContainer>>();
        for (int i = 0; i < numStacks; i++) {
            manifest.add(new ArrayList<FreightContainer>());
        }

    }

    /**
     * Loads a freight container onto the ship, provided that it can be
     * accommodated within the five rules set by the captain.
     * 
     * @param newContainer
     *            the new freight container to be loaded
     * @throws ManifestException
     *             if adding this container would exceed the ship's weight
     *             limit; if a container with the same code is already on board;
     *             or if no suitable space can be found for this container
     * 
     * @Zhen comment: Other than that, need to ensure: Align containers as close
     *       as possible to zero.(Start from menifest.get(0).get(0))
     * 
     *       Separate Containers according to their type, same type same stack
     *       until reach the Max Height, then move to another Stack.
     */
    public void loadContainer(FreightContainer newContainer)
            throws ManifestException {
        if (newContainer.getGrossWeight() + loadedWeight > maxWeight) {
            throw new ManifestException("Over weight");
        }
        for (int i = 0; i < manifest.size(); i++) {
            for (int j = 0; j < manifest.get(i).size(); j++) {
                if (manifest.get(i).get(j).getCode()
                        .equals(newContainer.getCode())) {
                    throw new ManifestException("Duplicated container");
                }
            }
        }

        for (int i = 0; i < manifest.size(); i++) {
            // Every time to next stack, need to initialize or not will out of
            // boundary
            if (manifest.get(i).size() == 0) {
                manifest.get(i).add(newContainer);
                loadedWeight += newContainer.getGrossWeight();
                return;
            } else {
                if (manifest.get(i).size() < maxHeight) {
                    if (manifest.get(i).get(0).getClass() == newContainer
                            .getClass()) {
                        manifest.get(i).add(newContainer);
                        loadedWeight += newContainer.getGrossWeight();
                        loadedContainer += 1;
                        return;
                    }
                }
            }

        }

        throw new ManifestException("no suitable space can be found");
    }

    /**
     * Unloads a particular container from the ship, provided that it is
     * accessible (i.e., on top of a stack).
     * 
     * @param containerId
     *            the code of the container to be unloaded
     * @throws ManifestException
     *             if the container is not accessible because it's not on the
     *             top of a stack (including the case where it's not on board
     *             the ship at all)
     */
    public void unloadContainer(ContainerCode containerId)
            throws ManifestException {
        // Author : Zhen

        // Which stack is located
        Integer locatedStack = whichStack(containerId);

        // Throw exception if stack is null
        if (locatedStack == null) {
            throw new ManifestException(
                    "Responsible Container not on board the ship at all.");
        }

        // Throw exception if required container height less than maximum height
        // located of stack container located.
        if (howHigh(containerId) != manifest.get(locatedStack).size()-1) {
            throw new ManifestException(
                    "Unable to unload. Container not accessible.");
        }        
        ArrayList<FreightContainer> containers=manifest.get(locatedStack);
        FreightContainer containerToRemoved=null;
        for(FreightContainer container:containers){
            if(container.getCode().equals(containerId)){
                containerToRemoved=container;
                break;
            }
        }
        if(containerToRemoved!=null){
        manifest.get(locatedStack).remove(containerToRemoved);
        }

    }

    /**
     * Returns which stack holds a particular container, if any. The container
     * of interest is identified by its unique code. Constant <code>null</code>
     * is returned if the container is not on board.
     * 
     * @param queryContainer
     *            the container code for the container of interest
     * @return the number of the stack with the container in it, or
     *         <code>null</code> if the container is not on board
     */
    public Integer whichStack(ContainerCode queryContainer) {
        for (int i = 0; i < manifest.size(); i++) {
            for (int j = 0; j < manifest.get(i).size(); j++) {
                if (manifest.get(i).get(j).getCode().equals(queryContainer)) {
                    return i;
                }
            }
        }
        return null;
    }

    /**
     * Returns how high in its stack a particular container is. The container of
     * interest is identified by its unique code. Height is measured in the
     * number of containers, counting from zero. Thus the container at the
     * bottom of a stack is at "height" 0, the container above it is at height
     * 1, and so on. Constant <code>null</code> is returned if the container is
     * not on board.
     * 
     * @param queryContainer
     *            the container code for the container of interest
     * @return the container's height in the stack, or <code>null</code> if the
     *         container is not on board
     */
    public Integer howHigh(ContainerCode queryContainer) {
        for (int i = 0; i < manifest.size(); i++) {
            for (int j = 0; j < manifest.get(i).size(); j++) {
                if (manifest.get(i).get(j).getCode().equals(queryContainer)) {
                    return j;
                }
            }
        }
        return null;
    }

    /**
     * Returns the contents of a particular stack as an array, starting with the
     * bottommost container at position zero in the array.
     * 
     * @param stackNo
     *            the number of the stack of interest
     * @return the stack's freight containers as an array
     * @throws ManifestException
     *             if there is no such stack on the ship
     */
    public FreightContainer[] toArray(Integer stackNo) throws ManifestException {
        if (stackNo > manifest.size() - 1 || stackNo < 0) {
            throw new ManifestException("no such stack on the ship");
        }
        FreightContainer[] result = new FreightContainer[manifest.get(stackNo)
                .size()];
        manifest.get(stackNo).toArray(result);
        return result;
    }

    /* ***** toString methods added to support the GUI ***** */

    public String toString(ContainerCode toFind) {
        // Some variables here are used and not declared. You can work it out
        String toReturn = "";
        for (int i = 0; i < manifest.size(); ++i) {
            ArrayList<FreightContainer> currentStack = manifest.get(i);
            toReturn += "|";
            for (int j = 0; j < currentStack.size(); ++j) {
                if (toFind != null
                        && currentStack.get(j).getCode().equals(toFind))
                    toReturn += "|*" + currentStack.get(j).getCode().toString()
                            + "*|";
                else
                    toReturn += "| " + currentStack.get(j).getCode().toString()
                            + " |";
            }
            if (currentStack.size() == 0)
                toReturn += "|  ||\n";
            else
                toReturn += "|\n";
        }
        return toReturn;
    }
    
   

    @Override
    public String toString() {
        return toString(null);
    }
}
