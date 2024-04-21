import org.example.models.ResponseDTO;
import org.example.services.CalculationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MaxflowTest {

    private final CalculationService service  = new CalculationService();
    private final Logger LOGGER = Logger.getLogger(MaxflowTest.class.getName());
    @Test
    @DisplayName("Checking DFS!")
    public void testDFS(){
        Map<String, List<String[]>> edges = new HashMap<>();
        List<String[]> edgesL = new ArrayList<>();
        edgesL.add(new String[]{"A","1"});
        edgesL.add(new String[]{"C","1"});
        edges.put("S",edgesL );

        edgesL = new ArrayList<>();
        edgesL.add(new String[]{"C","1"});
        edgesL.add(new String[]{"B","1"});
        edges.put("A",edgesL );

        edgesL = new ArrayList<>();
        edgesL.add(new String[]{"D","1"});
        edges.put("C",edgesL );

        edgesL = new ArrayList<>();
        edgesL.add(new String[]{"T","1"});
        edges.put("B",edgesL );

        edgesL = new ArrayList<>();
        edgesL.add(new String[]{"T","1"});
        edges.put("D",edgesL );

        List<String> path = service.dfs(edges,"S","T", new HashSet<>(), new ArrayList<>());


        assertEquals(Arrays.stream(new String[]{"S","A","C","D","T"}).toList(),path);

    }


}
