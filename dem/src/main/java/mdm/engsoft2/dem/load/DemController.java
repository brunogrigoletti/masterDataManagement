package mdm.engsoft2.dem.load;

import mdm.engsoft2.dem.transform.TransformService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dem/countries")
public class DemController {

    private final TransformService data;

    public DemController(TransformService t) {
        this.data = t;
    }

    @GetMapping("")
    public ResponseEntity<String> getCountries() {
        String bronzeData = data.getBronzeData();
        if (bronzeData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\":\"No bronze data available.\"}");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bronzeData);
    }
}