package unswstudyclub.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Video;
import unswstudyclub.service.VideoService;

import javax.validation.Valid;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v6")
@RestController
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping(path="/video")
    public int uploadVideo(@Valid @NotNull @RequestBody Video video) {
        return videoService.uploadVideo(video);
    }

    @GetMapping(path="/video/{code}")
    public List<Video> selectVideoByCourseCode(@Valid @NotNull @PathVariable("code") String code)  {
        return videoService.selectVideoByCourseCode(code);
    }

    @DeleteMapping(path="/video/{id}")
    public int deleteVideoById(@PathVariable("id") UUID id) {
        return videoService.deleteVideoById(id);
    }

    @PutMapping(path="/video/{id}")
    public int updateVideoById(@PathVariable("id") UUID id,
                               @NotNull @RequestBody Video video) {
        return videoService.updateVideoById(id, video);
    }
}
