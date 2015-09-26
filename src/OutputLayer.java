
public class OutputLayer extends HiddenLayer
{
    
   public OutputLayer(int outputCount,Layer previousLayer)
   {
       super(outputCount-1,previousLayer);//don't need auxiliary neuron in last layer
       this.setOutputs();//ovveride the outputs by using the overiden setOutputs method 
   }
   
   //Overriding the setOutput method inherited from class HiddenLayer
   public void setOutputs()
   {
       for(int i = 0;i < count;i++)
       {
           layer[i].setOutput(layer[i].getWeightedSum()); 
       }
   }
    
   public void setOutputsForTesting(double[] outputs)
   {
       //if(outputs.length != count)
            //throw new Exception("Size of outputs array is not equal to the number of neurons in Output Layer.");
       for(int i = 0;i < outputs.length;i++)
           layer[i].setOutput(outputs[i]);//initialize the outputs of the Output Layer to the values passed inside the outputs array 
   }
   
    //Overriding the inherited method
    public void updateOutputs()
    {
         initializeWeightedSums();//both iniitializes and updates the weights
         setOutputs();
    }
}
