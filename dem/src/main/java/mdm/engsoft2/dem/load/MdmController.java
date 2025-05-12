package mdm.engsoft2.dem.load;

import mdm.engsoft2.dem.transform.Country;
import mdm.engsoft2.dem.transform.TransformService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

@RestController
public class MdmController {

    private final TransformService data;

    public MdmController(TransformService t) {
        this.data = t;
    }

    @GetMapping("/countries")
    public ArrayList<Country> getCountries() {
        return data.getCountries();
    }
}