/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Drew
 */
public class InHouse extends Part
{
    protected int machineId;
    
    //constructor
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    
    //methods
    //setters (update)
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }
    
    //getters (read)
    public int getMachineId()
    {
        return machineId;
    }
}
