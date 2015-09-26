
import java.lang.Math;

public class NNTraining
{
 public static int numCycles = 10000; 
 public static int numInputs  = 4; 
 public static int numOutputs = 3;
 public static int numHiddenNurons  = 20; 
 public static int numSamples = 4; 
 public static double lR = 0.01; 
 
 public static int currentSample;
 public static double currentError;
  public static double sampleError;
  public static double sampleErrorAbs;
 public static double totalError;
 public static double wantedError= 0.000001;
 
 

 public static double[] previousOutput  = new double[numOutputs];
 public static double[] multiError  = new double[numOutputs];

 public static double[][] inputData  = new double[numSamples][numInputs];
 public static double[][] outputData = new double[numSamples][numOutputs];
 
 
 public static double[] outputHiddenNurons  = new double[numHiddenNurons];

 public static double[][] weightInput = new double[numInputs][numHiddenNurons];
 public static double[][]  weightOutput = new double[numHiddenNurons][numOutputs];


 public static void main(String[] args){
    initializeWights();
    initializeData();
    for(int j = 0;j <= numCycles;j++){
        totalError=0;
        for(int i = 0;i<numSamples;i++){
            currentSample = i;
            calculateOutputs();
            WeightChanges();
        }
        System.out.println("cycle = " + j + "  Error = " + sampleErrorAbs);
    }
    resultNN();
    //testNN();
 }

 public static void calculateOutputs(){
   for(int i = 0;i<numHiddenNurons;i++){
      outputHiddenNurons[i] = 0.0;
      for(int j = 0;j<numInputs;j++){
            outputHiddenNurons[i] = outputHiddenNurons[i] + (inputData[currentSample][j] * weightInput[j][i]);
      }
      outputHiddenNurons[i] = 1/(1+java.lang.Math.exp(-outputHiddenNurons[i]));
   }
   sampleError=0;
   sampleErrorAbs=0;
   for(int j = 0; j<numOutputs;j++){
       previousOutput[j]=0;
       multiError[j]=0;
       for(int i = 0;i<numHiddenNurons;i++){
            previousOutput[j] = previousOutput[j] + outputHiddenNurons[i] * weightOutput[i][j];
        }
       currentError =  previousOutput[j] - outputData[currentSample][j];
       sampleError=sampleError+currentError;
       sampleErrorAbs=sampleErrorAbs+java.lang.Math.abs(currentError);
       multiError[j]=currentError;
       totalError=totalError+currentError;
       }
    
 }

 public static void WeightChanges(){
     double weightChange; 
     for(int i = 0;i<numHiddenNurons;i++){
         for(int j = 0;j<numOutputs;j++){
             weightChange = lR * 2 *multiError[j] * outputHiddenNurons[i];
             weightOutput[i][j] = weightOutput[i][j] - weightChange;
        }
     }
     
     for(int i = 0;i<numHiddenNurons;i++){
         
         for(int j = 0;j<numInputs;j++){
             weightChange = 2* sampleError * lR * outputHiddenNurons[i]*(1 - outputHiddenNurons[i])  *inputData[currentSample][j];
             weightInput[j][i] = weightInput[j][i] - weightChange;
         }
        
     }
 }


 public static void initializeData(){

    inputData[0][0]  = 1;
    inputData[0][1]  = 2;
    inputData[0][2]  = 3;
    inputData[0][3]  = 1;
    
    outputData[0][0] = 1;
    outputData[0][1] = 4;
    outputData[0][2] = 9;
    //*****
    inputData[1][0]  = 2;
    inputData[1][1]  = 3;
    inputData[1][2]  = 4;
    inputData[1][3]  = 1;
    
    outputData[1][0] = 4;
    outputData[1][1] = 9;
    outputData[1][2] = 16;
    //*****
    inputData[2][0]  = 3;
    inputData[2][1]  = 4;
    inputData[2][2]  = 5;
    inputData[2][3]  = 1;
    
    outputData[2][0] = 9;
    outputData[2][1] = 16;
    outputData[2][2] = 25;
    //****
    inputData[3][0]  = 4;
    inputData[3][1]  = 5;
    inputData[3][2]  = 6;
    inputData[3][3]  = 1;
    
    outputData[3][0] = 16;
    outputData[3][1] = 25;
    outputData[3][2] = 36;
 }

 public static void initializeTest(){

    inputData[0][0]  = 5;
    inputData[0][1]  = 4;
    inputData[0][2]  = 3;
    inputData[0][3]  = 1;
    
    outputData[0][0] = 25;
    outputData[0][1] = 16;
    outputData[0][2] = 9;
    //*****
    inputData[1][0]  = 6;
    inputData[1][1]  = 5;
    inputData[1][2]  = 4;
    inputData[1][3]  = 1;
    
    outputData[1][0] = 36;
    outputData[1][1] = 25;
    outputData[1][2] = 16;
    //*****
    inputData[2][0]  = 3;
    inputData[2][1]  = 2;
    inputData[2][2]  = 4;
    inputData[2][3]  = 1;
    
    outputData[2][0] = 9;
    outputData[2][1] = 4;
    outputData[2][2] = 16;
    //****
    inputData[3][0]  = 4;
    inputData[3][1]  = 3;
    inputData[3][2]  = 5;
    inputData[3][3]  = 1;
    
    outputData[3][0] = 16;
    outputData[3][1] = 9;
    outputData[3][2] = 25;

 }

 
 public static void resultNN()
    {
     System.out.println("\n \n *** final results of NN\n");
     for(int i = 0;i<numSamples;i++){
         currentSample = i;
         calculateOutputs();
         for(int j = 0;j<numOutputs;j++){
             System.out.println("sample#"+ (currentSample+1) + " actual output = " + outputData[currentSample][j] + " trained output = " + previousOutput[j] + "    Error = " + multiError[j]);
          }
          System.out.println("Sample ERROR = "+sampleErrorAbs );
     }
    }
    
    
 public static void testNN()
    {
     System.out.println("\n \n ***Start of testing - test data will be created and tested on the NN\n");
     initializeTest();
     for(int i = 0;i<numSamples;i++){
         currentSample = i;
         calculateOutputs();
         for(int j = 0;j<numOutputs;j++){
             System.out.println("sample#"+ (currentSample+1) + " actual output = " + outputData[currentSample][j] + " trained output = " + previousOutput[j] + "    Error = " + multiError[j]);
          }
          System.out.println("");
     }
    }

 public static void initializeWights(){  
        double start = -1;
        double end = 1;
        double random ;
        for(int i = 0; i < numInputs;i++){
            for(int j = 0; j < numHiddenNurons;j++){
                weightInput[i][j]=start +Math.random( )*(end-start);
            }
        }
        
        for(int i = 0; i < numHiddenNurons;i++){
            for(int j = 0; j < numOutputs;j++){
                weightOutput[i][j]=start +Math.random( )*(end-start);
            }
        }

    }



}
