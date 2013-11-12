package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;
import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.CandidateSentence;

public class AnswerChoiceCandAnsCosineSimilarity extends AnswerScoreBaseClass
{
  @Override
  public double computScore(Answer answer, CandidateSentence sentence) 
  {
	  String answerText = answer.getCoveredText();
	  String sentenceText = sentence.getCoveredText();
	  
	  HashMap<String, Integer> termFrequencyVector1 = createTermFreqVector(answerText);
	  HashMap<String, Integer> termFrequencyVector2 = createTermFreqVector(sentenceText);
	  
	  return computeCosineSimilarity(termFrequencyVector1, termFrequencyVector2);
  }
  
 /**
  * Computes the cosine similarity between the two term frequency vectors.
  * @return Computed cosine similarity value.
  */
  private double computeCosineSimilarity(HashMap<String, Integer> termFrequencyVector1, 
		  HashMap<String, Integer> termFrequencyVector2) 
  {
	  double dotProduct = 0;
	  double termFrequencyVector1Mag = 0;
	  double termFrequencyVector2Mag = 0;
	  
	  Set<String> terms = termFrequencyVector1.keySet();
	  terms.addAll(termFrequencyVector2.keySet());
	  
	  for (String term : terms)
	  {
		  if (termFrequencyVector1.containsKey(term) && termFrequencyVector2.containsKey(term))
		  {
			  dotProduct += (double) (termFrequencyVector1.get(term) * 
					  termFrequencyVector2.get(term));
		  }
		  
		  if (termFrequencyVector1.containsKey(term))
		  {
			  termFrequencyVector1Mag += (double) (termFrequencyVector1.get(term) * 
					  termFrequencyVector1.get(term));
		  }
		  
		  if (termFrequencyVector2.containsKey(term))
		  {
			  termFrequencyVector2Mag += (double) (termFrequencyVector2.get(term) * 
					  termFrequencyVector2.get(term));
		  }
	  }
	  
	  termFrequencyVector1Mag = Math.sqrt(termFrequencyVector1Mag);
	  termFrequencyVector2Mag = Math.sqrt(termFrequencyVector2Mag);

	  return dotProduct/(termFrequencyVector1Mag * termFrequencyVector2Mag);
  }
  
  /**
   * Retrieves the list of tokens in a sentence and determines the 
   * frequency of each token in the sentence.
   * @param sentenceText Text contained in the sentence.
   * @return Term frequency vector containing tokens and frequency of each token
   * in the sentence.
   */
  private HashMap<String, Integer> createTermFreqVector(String sentenceText) 
  {
      StringTokenizer st = new StringTokenizer(sentenceText);
      
      HashMap<String, Integer> termFrequencyVector = new HashMap<String, Integer>();
      
      while (st.hasMoreTokens())
      {
          String tokenText = st.nextToken();
          tokenText = tokenText.toUpperCase();
          
          if (termFrequencyVector.containsKey(tokenText))
          {
        	  termFrequencyVector.put(tokenText, 
        			  termFrequencyVector.get(tokenText) + 1);
          }
          else
          {
        	  termFrequencyVector.put(tokenText, 1);
          }
      }
      
      System.out.println(termFrequencyVector);
      
      return termFrequencyVector;
  }
}