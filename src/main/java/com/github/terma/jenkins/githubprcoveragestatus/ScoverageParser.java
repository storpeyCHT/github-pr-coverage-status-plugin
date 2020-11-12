package com.github.terma.jenkins.githubprcoveragestatus;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="http://scoverage.org/">SCoverage</a>
 */
class ScoverageParser implements CoverageReportParser {

    private static String findFirst(String string, String pattern) {
        String result = findFirstOrNull(string, pattern);
        if (result != null) {
            return result;
        } else {
            throw new IllegalArgumentException("Can't find " + pattern + " in " + string);
        }
    }

    private static String findFirstOrNull(String string, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(string);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    @Override
    public float get(String scoverageFilePath) {
        try {
            String content = FileUtils.readFileToString(new File(scoverageFilePath));
            float statementRate = Float.parseFloat(findFirst(content, "statement-rate=['\"]([0-9.]+)['\"]"));
            return statementRate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
