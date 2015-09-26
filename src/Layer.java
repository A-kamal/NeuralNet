//super class of all layers

public class Layer
{
    protected int count; //number of neurons
    protected Neuron[] layer;//array of neurons constructing this layer
    
    public Layer(int count)
    {
        this.count = count + 1;//to account for the bias
        this.layer = new Neuron[count+1];
    }
    
    public int getCount()
    {
        return count-1;//Actual number of neurons, not including the auxiliary neuron
    }
    
    //Actual number of neurons desired is passed as parameter, not including the auxiliary neuron
    public void setCount(int count)
    {
        this.count = count + 1;
    }
    
    //Returns an array containing the outputs of all neurons in this layer
    public double[] getOutputs()
    {
        double[] outputs = new double[count];
        for(int i = 0;i < count;i++)
            outputs[i] = layer[i].getOutput();
        return outputs;
    }
}
