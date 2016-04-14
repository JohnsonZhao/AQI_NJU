package edu.nju.aqi.analysis.helper;


public class SimilarityUtils {

	/**
	 * 计算多维矩阵之间的余弦相似度<br/>
	 * scope：[-1,1]，值越大，夹角越大，相似度越小
	 * 
	 * @param vector1
	 * @param vector2
	 * @return
	 */
	public static double cosineSimilarity(double[] vector1, double[] vector2) {
		if (vector1 == null || vector2 == null) {
			throw new RuntimeException("vector cannot be null!");
		}
		if (vector1.length != vector2.length) {
			return 1.0;
		}
		double dotProduct = 0.0;
		double norm1 = 0.0;
		double norm2 = 0.0;

		int len = vector1.length;
		for (int i = 0; i < len; i++) {
			dotProduct += vector1[i] * vector2[i];
			norm1 += Math.pow(vector1[i], 2);
			norm2 += Math.pow(vector2[i], 2);
		}
		norm1 = Math.sqrt(norm1);
		norm2 = Math.sqrt(norm2);

		if (norm1 != 0.0 && norm2 != 0.0) {
			return dotProduct / (norm1 * norm2);
		} else {
			return 0;
		}
	}

	/**
	 * pearson相关系数，用来衡量连续变化变量间的关联程度
	 * 
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static double pearsonCorrelation(double[] vector1, double[] vector2) {
		if (vector1 == null || vector2 == null) {
			throw new RuntimeException("vector cannot be null!");
		}
		if (vector1.length != vector2.length) {
			return 0.0;
		}
		// 最小值
		double minium = 0.0;
		// 最大值
		double max = 1.0;
		double correlation = 0.0;
		double sum1 = 0.0, sum2 = 0.0;
		double squareSum1 = 0.0, squareSum2 = 0.0;
		double productSum = 0.0;
		int commonItemLen = 0;

		int len = vector1.length;
		for (int i = 0; i < len; i++) {
			double value1 = vector1[i];
			double value2 = vector2[i];
			commonItemLen++;
			// 求和
			sum1 += value1;
			sum2 += value2;

			// 平方和
			squareSum1 += Math.pow(value1, 2);
			squareSum2 += Math.pow(value2, 2);

			// 乘积和
			productSum += value1 * value2;
		}
		if (commonItemLen < 1) {
			correlation = minium;
			return correlation;
		}
		double num = commonItemLen * productSum - sum1 * sum2;
		double den = Math.sqrt(
				(commonItemLen * squareSum1 - Math.pow(sum1, 2)) * (commonItemLen * squareSum2 - Math.pow(sum2, 2)));
		correlation = (Double.compare(den, 0.0) == 0) ? max : num / den;
		return correlation;
	}

}
