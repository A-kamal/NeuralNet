/**
 * Class Neuron represents an individual neuron.
 * Each neuron has a weighted sum (landa) and an output (z).  
*/
public class Neuron
{
    private double weightedSum;//The weighted sum of the neuron,landa
    private double output;//The output of the neuron,z
    
    /**
     * Constructs a new neuron containing the passed weightedSum and the passed output 
     * @param weightedSum The desired weightedSum
     * @param output The desired output
     */
    public Neuron(double weightedSum,double output)
    {
        this.weightedSum = weightedSum;
        this.output = output;
    }
    
     /**
     * Constructs a new neuron with weightedSum = 0 and output = 0
     */
    public Neuron()
    {
        weightedSum = 0;
        output = 0;
    }
    
     /**
     *  Sets the weightedSum of this neuron to the passed value
     * @param weightedSum The desired weightedSum
     */
    public void setWeightedSum(double weightedSum) 
    {
        this.weightedSum = weightedSum;
    }
    
    /**
     *  Sets the output of this neuron to the passed value
     * @param output The desired output
     */
    public void setOutput(double output)
    {
        this.output = output;
    }
    
    /**
     *  Returns the output of this neuron
     * @return The output value of this neuron
     */
    public double getOutput()
    {
        return new Double(output);//return a clone copy
    }
    
     /**
     *  Returns the weightedSum of this neuron
     * @return The weightedSum value of this neuron
     */
     public double getWeightedSum()
    {
        return new Double(weightedSum);//return a clone copy
    }
}
