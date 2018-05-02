import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class WebController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    Result<String> home() {
        return Result.success("Hello World!");
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    Result<String> post(@RequestParam String id) {
        return Result.success("Post  " + id);
    }

    @RequestMapping(path = "/body", method = RequestMethod.POST)
    Result<String> body(@RequestBody List<String> id) {
        return Result.success("body size " + id.size());
    }

    @RequestMapping(path = "/", method = RequestMethod.PUT)
    Result<String> put(@RequestParam String id) {
        return Result.success("Put " + id);
    }


    @RequestMapping(path = "/", method = RequestMethod.DELETE)
    Result<String> delete(@RequestParam String id) {
        return Result.success("DELETE " + id);
    }


    @RequestMapping(path = "/{id}/info")
    Result<String> info(@PathVariable String id) {
        return Result.success("info " + id);
    }

    @RequestMapping(path = "/upload")
    Result<String> file(@RequestParam("file") MultipartFile file) {
        System.out.println(new Gson().toJson(file));
        return Result.success("FileName " + file.getOriginalFilename() + " FileSize:" + file.getSize());
    }


    public static void main(String[] args) {
        SpringApplication.run(WebController.class, args);
    }
}
