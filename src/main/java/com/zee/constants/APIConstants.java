package com.zee.constants;

public class APIConstants {

	public static final String BASEURL_OF_PAGEINSIGHT = "https://pagespeedonline.googleapis.com/";
	public static final String PAGE_SPEED_URL = "pagespeedonline/v5/runPagespeed";
	public static final String QUERY_FOR_OVERALL_PERFORMANCE = "lighthouseResult.categories.performance.score";
	public static final String LARGEST_CONTENTFUL_PAINT_MS = "loadingExperience.metrics.LARGEST_CONTENTFUL_PAINT_MS.percentile";
	public static final String FIRST_INPUT_DELAY_MS = "loadingExperience.metrics.FIRST_INPUT_DELAY_MS.percentile";
	public static final String FIRST_CONTENTFUL_PAINT_MS = "loadingExperience.metrics.FIRST_CONTENTFUL_PAINT_MS.percentile";
	public static final String EXPERIMENTAL_TIME_TO_FIRST_BYTE = "loadingExperience.metrics.EXPERIMENTAL_TIME_TO_FIRST_BYTE.percentile";
	public static final String EXPERIMENTAL_INTERACTION_TO_NEXT_PAINT = "loadingExperience.metrics.EXPERIMENTAL_INTERACTION_TO_NEXT_PAINT.percentile";
	public static final String CUMULATIVE_LAYOUT_SHIFT_SCORE = "loadingExperience.metrics.CUMULATIVE_LAYOUT_SHIFT_SCORE.percentile";
}
