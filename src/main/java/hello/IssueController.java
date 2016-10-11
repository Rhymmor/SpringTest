package hello;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueController {
    private final HashMap<Long, Issue> issues = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    @RequestMapping("/post")
    public Issue post(@RequestParam(value="title", defaultValue="") String title,
                      @RequestParam(value="description", defaultValue="") String description) {
        Issue issue = new Issue(idCounter.incrementAndGet(), title, description);
        issues.put(issue.getId(), issue);
        return issue;
    }

    @RequestMapping("/get")
    public Issue get(@RequestParam(value="id", defaultValue= "0") Long id) {
        return issues.get(id);
    }

    @RequestMapping("/put")
    public Issue put(@RequestParam(value="id", defaultValue= "0") Long id,
                    @RequestParam(value="title", required = false) String title,
                    @RequestParam(value="description", required = false) String description) {
        setIssue(id, title, description);
        return issues.get(id);
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam(value="id", defaultValue= "0") Long id) {
        issues.remove(id);
    }

    private void setIssue(@RequestParam(value = "id", defaultValue = "0") Long id, @RequestParam(value = "title", defaultValue = "") String title, @RequestParam(value = "description", defaultValue = "") String description) {
        if (title != null) {
            issues.get(id).setTitle(title);
        }
        if (description != null) {
            issues.get(id).setDescription(description);
        }
    }
}
