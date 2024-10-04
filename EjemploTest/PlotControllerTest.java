@SpringBootTest
@AutoConfigureMockMvc
public class PlotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlotService plotService;

    @Test
    @DisplayName("PC-001: Should return all plots")
    public void testGetAllPlots() throws Exception {
        List<PlotDto> listPlots = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            PlotDto plot = new PlotDto();
            plot.setId(Long.parseLong(String.valueOf(i)));
            listPlots.add(plot);
        }

        Page<PlotDto> pagePlots =
                new PageImpl<>(listPlots, PageRequest.of(0, listPlots.size()), listPlots.size());

        when(plotService.getAllPlots(PageRequest.of(0, listPlots.size()))).thenReturn(pagePlots);

        mockMvc.perform(get("/plots")
                        .param("page", "0")
                        .param("size", String.valueOf(listPlots.size()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(listPlots.size()))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andExpect(jsonPath("$.content[2].id").value(3L));
    }

    @Test
    @DisplayName("PC-002: Should return plot by ID")
    public void testGetPlotById() throws Exception {
        PlotDto plot = new PlotDto();
        plot.setId(1L);

        when(plotService.getPlotById(1L)).thenReturn(plot);

        mockMvc.perform(get("/plots/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("PC-003: Should create a new plot")
    public void testCreatePlot() throws Exception {
        PlotDto plot = new PlotDto();
        plot.setId(10L);
        PlotDtoPost plotDtoPost = new PlotDtoPost();
        plotDtoPost.setBlockNumber(new BigInteger("1"));
        plotDtoPost.setPlotType(PlotType.COMMERCIAL);
        plotDtoPost.setTotalArea(new BigInteger("2"));
        plotDtoPost.setBuiltArea(new BigInteger("3"));
        plotDtoPost.setPlotStatus(PlotStatus.CONSTRUCTION_PROCESS);


        when(plotService.createPlot(plotDtoPost, 1L)).thenReturn(plot);

        mockMvc.perform(post("/plots")
                        .header("x-user-id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plotDtoPost)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L));
    }

    @Test
    @DisplayName("PC-004: Should update an existing plot")
    public void testUpdatePlot() throws Exception {
        PlotDtoPut plotDtoPut = new PlotDtoPut();
        plotDtoPut.setBlockNumber(new BigInteger("1"));
        plotDtoPut.setPlotType(PlotType.COMMERCIAL);
        plotDtoPut.setTotalArea(new BigInteger("2"));
        plotDtoPut.setBuiltArea(new BigInteger("3"));
        plotDtoPut.setPlotStatus(PlotStatus.CONSTRUCTION_PROCESS);
        PlotDto plot = new PlotDto();
        plot.setId(1L);
        plot.setPlotNumber(new BigInteger("1"));

        when(plotService.updatePlot(1L, plotDtoPut, 1L)).thenReturn(plot);

        mockMvc.perform(put("/plots/1")
                        .header("x-user-id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plotDtoPut)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("PC-005: Should delete a plot")
    public void testDeletePlot() throws Exception {
        doNothing().when(plotService).deletePlot(1L, 1L);

        mockMvc.perform(delete("/plots/1")
                        .header("x-user-id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    @DisplayName("PC-006: Should return plots by plot type")
    public void testGetPlotsByPlotType() throws Exception {
        PlotDto plot1 = new PlotDto();
        plot1.setId(1L);
        PlotDto plot2 = new PlotDto();
        plot2.setId(2L);
        List<PlotDto> listPlots = Arrays.asList(plot1, plot2);

        when(plotService.getPlotsByPlotType(PlotType.PRIVATE, PageRequest.of(0, listPlots.size())))
                .thenReturn(new PageImpl<>(listPlots));

        mockMvc.perform(get("/plots/plotType/PRIVATE")
                        .param("page", "0")
                        .param("size", String.valueOf(listPlots.size()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(listPlots.size()))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[1].id").value(2L));
    }

    @Test
    @DisplayName("PC-007: Should return plots by plot status")
    public void testGetPlotsByPlotStatus() throws Exception {
        PlotDto plot1 = new PlotDto();
        plot1.setId(1L);
        PlotDto plot2 = new PlotDto();
        plot2.setId(2L);
        List<PlotDto> listPlots = Arrays.asList(plot1, plot2);

        when(plotService.getPlotsByPlotStatus(PlotStatus.SALE, PageRequest.of(0, listPlots.size())))
                .thenReturn(new PageImpl<>(listPlots));

        mockMvc.perform(get("/plots/plotStatus/SALE")
                        .param("page", "0")
                        .param("size", String.valueOf(listPlots.size()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(listPlots.size()))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[1].id").value(2L));
    }

    @Test
    @DisplayName("PC-008: Should return plots by block number")
    public void testGetPlotsByBlockNumber() throws Exception {
        PlotDto plot1 = new PlotDto();
        plot1.setId(1L);
        PlotDto plot2 = new PlotDto();
        plot2.setId(2L);
        List<PlotDto> listPlots = Arrays.asList(plot1, plot2);

        when(plotService.getPlotsByBlockNumber(new BigInteger("101"), PageRequest.of(0, listPlots.size())))
                .thenReturn(new PageImpl<>(listPlots));

        mockMvc.perform(get("/plots/blockNumber/101")
                        .param("page", "0")
                        .param("size", String.valueOf(listPlots.size()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[1].id").value(2L));
    }

    @Test
    @DisplayName("PC-009: Should assign area over total for a plot")
    public void testPlotAssignAreaOverAll() throws Exception {
        PlotDto plot = new PlotDto();
        plot.setId(1L);
        plot.setPercentageForExpense(new BigDecimal("50.00"));

        when(plotService.plotAssignPercentageForExpense(1L, new BigDecimal("50.00"))).thenReturn(plot);

        mockMvc.perform(put("/plots/1/percentage/50.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.percentage_for_expense").value(50.00));
    }

    @Test
    @DisplayName("PC-010: Should return all plot types")
    public void testGetAllPlotTypes() throws Exception {
        mockMvc.perform(get("/plots/type")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]").value(PlotType.COMMERCIAL.name()))
                .andExpect(jsonPath("$[1]").value(PlotType.PRIVATE.name()))
                .andExpect(jsonPath("$[2]").value(PlotType.COMMUNAL.name()));
    }

    @Test
    @DisplayName("PC-011: Should return 404 when plot not found")
    public void testGetPlotById_NotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(plotService).getPlotById(1L);

        mockMvc.perform(get("/plots/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PC-012: Should return 400 when creating plot with invalid data")
    public void testCreatePlot_BadRequest() throws Exception {
        PlotDtoPost plotDtoPost = new PlotDtoPost();

        mockMvc.perform(post("/plots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plotDtoPost)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PC-013: Should return 404 when updating non-existent plot")
    public void testUpdatePlot_NotFound() throws Exception {
        PlotDtoPut plotDtoPut = new PlotDtoPut();
        plotDtoPut.setBlockNumber(new BigInteger("1"));
        plotDtoPut.setPlotType(PlotType.COMMERCIAL);
        plotDtoPut.setTotalArea(new BigInteger("2"));
        plotDtoPut.setBuiltArea(new BigInteger("3"));
        plotDtoPut.setPlotStatus(PlotStatus.CONSTRUCTION_PROCESS);


        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(plotService).updatePlot(1L, plotDtoPut, 1L);

        mockMvc.perform(put("/plots/1")
                        .header("x-user-id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plotDtoPut)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PC-014: Should return 404 when deleting non-existent plot")
    public void testDeletePlot_NotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(plotService).deletePlot(1L, 1L);

        mockMvc.perform(delete("/plots/1")
                        .header("x-user-id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PC-015: Should return 500 on internal server error when fetching plots by plot type")
    public void testGetPlotsByPlotType_InternalServerError() throws Exception {
        when(plotService.getPlotsByPlotType(PlotType.PRIVATE, PageRequest.of(0, 10)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/plots/plotType/PRIVATE")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal Server Error"));
    }

    @Test
    @DisplayName("PC-016: Should return 500 on internal server error when fetching plots by plot status")
    public void testGetPlotsByPlotStatus_InternalServerError() throws Exception {
        when(plotService.getPlotsByPlotStatus(PlotStatus.SALE, PageRequest.of(0, 10)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/plots/plotStatus/SALE")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal Server Error"));
    }

    @Test
    @DisplayName("PC-017: Should return 500 on internal server error when fetching plots by block number")
    public void testGetPlotsByBlockNumber_InternalServerError() throws Exception {
        when(plotService.getPlotsByBlockNumber(new BigInteger("101"), PageRequest.of(0, 10)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/plots/blockNumber/101")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal Server Error"));
    }

    @Test
    @DisplayName("PC-018: Should return 400 when assigning invalid area over total")
    public void testPlotAssignAreaOverAll_BadRequest() throws Exception {
        BigDecimal invalidArea = new BigDecimal("-10.00");

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).when(plotService).plotAssignPercentageForExpense(1L, invalidArea);

        mockMvc.perform(put("/plots/1/percentage/" + invalidArea)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PC-019: Should return 404 when assigning area over total to non-existent plot")
    public void testPlotAssignAreaOverAll_NotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(plotService).plotAssignPercentageForExpense(1L, new BigDecimal("50.00"));

        mockMvc.perform(put("/plots/1/percentage/50.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PC-020: Should return all plot status")
    public void testGetAllPlotStatus() throws Exception {
        mockMvc.perform(get("/plots/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(PlotStatus.values().length)));
    }
}
