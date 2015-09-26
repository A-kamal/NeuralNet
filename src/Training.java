import java.util.*;
import java.lang.Math;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public  class Training
{   
    private static double lR;
    private static double desiredError;
    private static double currentTotalError;
    private static double currentError,beta;
    private static double [] errorEachOutput;
    private static double [][] input;
    private static double [][] output;
    private static int currentSample, nCycles,i;
    private static double cc;
    private static double tau;
    private static double lR0;
    private static String adaptionS;
    private static String typeOftraining;
    private static double [] errorAtCycle;
    private static double [] errorAtSample;
    public static void trainTheNeuralNetwork(NeuralNet nn,double[][] inputs,double[][] outputs,double learningRate,double wantedError,int numCycles,double momentum, double c,double t,String adaption,String offOrOn)
    {
        nCycles= numCycles;
        lR=learningRate;
        beta=momentum;
        desiredError=wantedError;
        errorEachOutput=new double [outputs[0].length];
        errorAtCycle = new double [numCycles];
        errorAtSample = new double [inputs.length];
        double [] outputTrained = nn.getOutputs();
        double error=0;
        cc=c;
        lR0=learningRate;
        adaptionS=adaption;
        typeOftraining=offOrOn;
       
       // input=inputs;
        //output= outputs;
        for(i = 0;i < numCycles;i++)
        {
            int cyclerNum=i;
            trainTheWeights(nn,outputs,inputs,i);
            changeProgress(MainWindow.progressBar);
            // learning rate adaption;
            if(i!=0 && adaption.equals("stochastic")) lR=cc/i;
            
            if(adaption.equals("STC")) lR= lR0 *((1+ (cc/lR0)*(i/tau))/(1+(cc/lR0)*(i/tau)+tau*(i/tau)*(i/tau)));
            
            if(cyclerNum>=1){
                double errDif= errorAtCycle[cyclerNum] - errorAtCycle[cyclerNum-1];        
                if( adaptionS.equals("self adaption") && Math.abs(errDif) > 0.5){
                    lR=lR*0.8;
                }else if(adaptionS.equals("self adaption")&& Math.abs(errDif) < 0.5){
                    lR=lR*1.25;
                }
            }
        }
        MainWindow.progressBar.setValue(100); //If quits before numCycles reached, set to 100 anyways.
        JOptionPane.showMessageDialog(null, "Done training."); //Dialog box stating training is finished
    }
       
    public static double calculateError( double[] outputTrained,double[][] outputs,int currentSample){
        double error =0;
        for(int i=0; i<outputs[0].length;i++){
            errorEachOutput[i]=(outputTrained[i]-outputs[currentSample][i]);
            currentError=(outputTrained[i]-outputs[currentSample][i]);
            if (currentError<0)
                error=error -currentError ;else error=error+currentError;
                
        }
        return error;
    }
    
    private static void trainTheWeights(NeuralNet neuralNet,double[][] outputs,double[][] inputs,int cyclerNum)
    {
        trainWeights(neuralNet,outputs,inputs,cyclerNum);           
        //trainOutputLayerWeights(neuralNet,error);//train the output layer's weights
    }
    
    
    private static void trainWeights(NeuralNet neuralNet,double[][] outputs,double[][] inputs,int cyclerNum)
    {
        ArrayList<double[][]> weights = neuralNet.getNetworkWeights(); 
        ArrayList<double[][][]> allErrors = neuralNet.getError(outputs,inputs);
        double[][] tempWeight = null;
        double change = 0;
        double[][][] thisSampleErrors = null;
        double[][] thisLayerErrors = null;
        double[] thisNeuronErrors = null;
        double [] outputTrained;
        double deltaWeight = 0,weightOld = 0,weightNew = 0;
        errorAtCycle[cyclerNum]=0;
        for(int sampleIndex = 0;sampleIndex < allErrors.size();sampleIndex++)//# of samples
        {
            thisSampleErrors = allErrors.get(sampleIndex);//extract a three dimensional array containing errors of this sample
            weights = neuralNet.getNetworkWeights();
            neuralNet.updateNeuralNetwork(inputs[sampleIndex]);
            ///**** prining
            outputTrained = neuralNet.getOutputs();
            currentTotalError= calculateError(outputTrained,outputs,sampleIndex);
            errorAtCycle[cyclerNum]=errorAtCycle[cyclerNum]+currentTotalError;
            System.out.println("\n Sample # "+sampleIndex+"\n****************");
             for(int k = 0 ; k < outputs[0].length;k++)
             {
                 System.out.println(outputs[sampleIndex][k] +" : "+outputTrained[k]+" : " + errorEachOutput[k] );
             }
                 System.out.println(errorAtCycle[cyclerNum]+ " @ " +cyclerNum+" lR=: " +lR);
                //*** end of prining
            for(int layerIndex = 0;layerIndex < thisSampleErrors.length;layerIndex++)
            {
                thisLayerErrors = thisSampleErrors[layerIndex];//extract a two dimensional array containing errors of this layer
                //c=layerIndex;
                tempWeight = weights.get(layerIndex);//weights of this layer
                for(int neuronIndex = 0;neuronIndex < thisLayerErrors.length;neuronIndex++)//from first to last neuron of this layer
                {
                    thisNeuronErrors = thisLayerErrors[neuronIndex];
                    for(int neuronWeight = 0;neuronWeight < thisNeuronErrors.length;neuronWeight++)
                    {
                        change = 0;
                        change = thisNeuronErrors[neuronWeight];
                        weightOld = tempWeight[neuronIndex][neuronWeight];//old
                        tempWeight[neuronIndex][neuronWeight] = (tempWeight[neuronIndex][neuronWeight])-(lR*change);
                        weightNew = tempWeight[neuronIndex][neuronWeight];//updated
                        deltaWeight = weightNew - weightOld;
                        tempWeight[neuronIndex][neuronWeight] = tempWeight[neuronIndex][neuronWeight] + (beta * deltaWeight);
                    }
                }
                weights.set(layerIndex,tempWeight);
            } 
            if(typeOftraining.equals("online"))
            neuralNet.setNetworkWeights(weights);
        } 
        if(typeOftraining.equals("offline") || !typeOftraining.equals("online"))neuralNet.setNetworkWeights(weights);
    }
    
    private static void changeProgress(JProgressBar bar){
        bar.setValue(i*100/nCycles); // Displays percentage of training 
    }

	public static double[] returnError() {
		// TODO Auto-generated method stub
		return errorAtCycle;
	}
}