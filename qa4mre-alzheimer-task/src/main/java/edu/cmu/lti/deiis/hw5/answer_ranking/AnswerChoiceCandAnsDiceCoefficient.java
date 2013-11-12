package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.util.HashSet;
import java.util.StringTokenizer;
import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.CandidateSentence;

public class AnswerChoiceCandAnsDiceCoefficient extends AnswerScoreBaseClass
{
  @Override
  public double computScore(Answer answer, CandidateSentence sentence) 
  {
	  String answerText = answer.getCoveredText();
	  
	  System.out.println("Answer Text: " + answerText);
	  String sentenceText = sentence.getCoveredText();
	
	  HashSet<String> answerBigrams = createBigrams(answerText);
	  HashSet<String> sentenceBigrams = createBigrams(sentenceText);
	  
	  return computeCosineSimilarity(answerBigrams, sentenceBigrams);
  }
  
  /**
   * Computes the Dice coefficient between the two sets of bigrams.
   * @param bigrams1 First set of bigrams
   * @param bigrams2 Second set of bigrams
   * @return Computed Dice coefficient value.
   */
  private double computeCosineSimilarity(HashSet<String> bigrams1, 
		  HashSet<String> bigrams2) 
  {
      HashSet<String> intersection = new HashSet<String>(bigrams1);
      intersection.retainAll(bigrams2);
	  return 2 * ((double)(intersection.size())/(bigrams1.size() + bigrams2.size()));
  }
  
  /**
   * Retrieves the list of tokens in a sentence and determines the 
   * list of bigrams in the sentence.
   * @param sentenceText Text contained in the sentence.
   * @return Bigrams contained in the sentence.
   */
  private HashSet<String> createBigrams(String sentenceText) 
  {
      HashSet<String> bigrams = new HashSet<String>();
      StringTokenizer st = new StringTokenizer(sentenceText);

      String prev = null;
      
      while (st.hasMoreTokens())
      {
          String tokenText = st.nextToken();
          tokenText = tokenText.toUpperCase();
          
          if (prev != null)
          {
              bigrams.add(prev + tokenText);
          }
          
          prev = tokenText;
      }
      
      return bigrams;
  }
}