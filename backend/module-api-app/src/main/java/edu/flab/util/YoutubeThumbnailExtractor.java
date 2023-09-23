package edu.flab.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class YoutubeThumbnailExtractor {

	public static String getThumbnailUrl(String youtubeUrl) {
		return "https://img.youtube.com/vi/" + getVideoId(youtubeUrl) + "/0.jpg";
	}

	public static String getVideoId(String youtubeUrl) {
		Pattern regex = Pattern.compile(
			"http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|(?:be-nocookie|be)\\.com\\/(?:watch|[\\w]+\\?(?:feature=[\\w]+.[\\w]+\\&)?v=|v\\/|e\\/|embed\\/|live\\/|shorts\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)",
			Pattern.CASE_INSENSITIVE);
		Matcher matcher = regex.matcher(youtubeUrl);

		if (matcher.find()) {
			return matcher.group(1);
		}

		throw new IllegalArgumentException("Youtube URL 로부터 ID를 추출하는데 실패하였습니다");
	}
}
