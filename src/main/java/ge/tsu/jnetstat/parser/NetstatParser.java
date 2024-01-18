package ge.tsu.jnetstat.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetstatParser implements Callable<List<Result>> {

    public static final String REGEX = "(\\w+)\\s*(([\\d\\.]+):(\\d+))\\s*(([\\d\\.]+):(\\d+))\\s*(\\w+)\\s*(\\d+)";
    public static final String EXE_NAME_REGEX = "\\[(.+)\\]";

    private final Pattern pattern = Pattern.compile(REGEX);
    private final Pattern exeNamePattern = Pattern.compile(EXE_NAME_REGEX);

    private final Process process;

    public NetstatParser(Process process) {
        this.process = process;
    }

    @Override
    public List<Result> call() throws IOException {
        List<Result> results = new ArrayList<>();
        try (
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr)
        ) {
            br.lines().forEach(line -> {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    results.add(new Result(
                            null,       // Name
                            matcher.group(1), // Protocol
                            matcher.group(3), // Local Address
                            Integer.parseInt(matcher.group(4)), // Local Address Port
                            matcher.group(6), // Foreign Address
                            Integer.parseInt(matcher.group(7)), // Foreign Address Port
                            matcher.group(8), // State
                            Integer.parseInt(matcher.group(9)) // PID
                    ));
                } else {
                    Matcher exeNameMatcher = exeNamePattern.matcher(line);
                    if (exeNameMatcher.find()) {
                        String processExeName = exeNameMatcher.group(1);
                        results.getLast().setName(processExeName);
                    } else {
                        // TODO log warning somewhere...
                    }
                }
            });
        }
        return results;
    }
}
