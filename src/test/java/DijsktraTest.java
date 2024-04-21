
import lombok.SneakyThrows;
import org.example.exceptions.GraphException;
import org.example.models.EdgeDTO;
import org.example.models.GraphDTO;
import org.example.models.ResponseDTO;

import org.example.services.CalculationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DijsktraTest {

    private final GraphDTO graphExDTO = new GraphDTO(
            "dijsktra", "1", "5",
            Arrays.stream(new String[]{"1", "2", "4", "5"}).toList(),
            Arrays.stream(new EdgeDTO[]{
                    new EdgeDTO("1", "2", "5"),
                    new EdgeDTO("2", "4", "5"),
                    new EdgeDTO("3", "4", "5"),
                    new EdgeDTO("1","5","1"),
                    new EdgeDTO("2","5","2"),
                    new EdgeDTO("3","5","3"),
                    new EdgeDTO("4","5","4"),
                    new EdgeDTO("5","4","5")
            }).toList()
    );

    private final GraphDTO graphDTO = new GraphDTO(
            "dijsktra", "1", "5",
            Arrays.stream(new String[]{"1", "2","3", "4", "5"}).toList(),
            Arrays.stream(new EdgeDTO[]{
                    new EdgeDTO("1", "2", "5"),
                    new EdgeDTO("2", "4", "5"),
                    new EdgeDTO("3", "4", "5"),
                    new EdgeDTO("1","5","1"),
                    new EdgeDTO("2","5","2"),
                    new EdgeDTO("3","5","3"),
                    new EdgeDTO("4","5","4"),
                    new EdgeDTO("5","4","5")
            }).toList()
    );

    private final CalculationService service = new CalculationService();

    @Test
    @DisplayName("Checking Exception Handling!")
    public void testExceptionDijsktra() {
        Exception exception = assertThrows(GraphException.class, () ->
                service.calculateDijsktra(graphExDTO));
        assertEquals("Source and target nodes do not match", exception.getMessage());
    }

    @SneakyThrows
    @Test
    @DisplayName("Checking Calculation!")
    public void testDijsktra() throws GraphException {
        Map<String,String> path = new HashMap<>();
        path.put("5","1");

        Map<String,Integer> distances  = new HashMap<>();
        distances.put("1",0);
        distances.put("5",1);
        distances.put("2",5);
        distances.put("3",Integer.MAX_VALUE);
        distances.put("4",6);
        ResponseDTO responseDTO = service.calculateDijsktra(graphDTO);
        assertEquals(new ResponseDTO(path,distances),responseDTO);
    }
}
