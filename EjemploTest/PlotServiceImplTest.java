@SpringBootTest
public class PlotServiceImplTest {

    @SpyBean
    private PlotService plotService;

    @MockBean
    private PlotRepository plotRepository;

    @Test
    @DisplayName("PS-001: Should return all active plots")
    public void testGetAllPlots() {
        PlotEntity plotEntity1 = new PlotEntity();
        plotEntity1.setId(1L);
        PlotEntity plotEntity2 = new PlotEntity();
        plotEntity2.setId(2L);
        Page<PlotEntity> plotEntities = new PageImpl<>(Arrays.asList(plotEntity1, plotEntity2));

        when(plotRepository.findAllByIsActiveTrue(any(Pageable.class))).thenReturn(plotEntities);

        Page<PlotDto> result = plotService.getAllPlots(PageRequest.of(1, 2));

        assertEquals(2, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals(2L, result.getContent().get(1).getId());
    }

    @Test
    @DisplayName("PS-002: Should return plot by ID when found")
    public void testGetPlotById_Found() {
        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(1L);

        when(plotRepository.findById(1L)).thenReturn(Optional.of(plotEntity));

        PlotDto result = plotService.getPlotById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("PS-003: Should throw ResponseStatusException when plot not found by ID")
    public void testGetPlotById_NotFound() {
        when(plotRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            plotService.getPlotById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertTrue(thrown.getReason().contains("Plot not found with id: 1"));
    }

    @Test
    @DisplayName("PS-004: Should create a new plot")
    public void testCreatePlot() {
        PlotDtoPost plotDto = new PlotDtoPost();
        plotDto.setPlotType(PlotType.PRIVATE);
        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(1L);
        plotEntity.setPlotType(PlotType.PRIVATE);
        plotEntity.setPlotStatus(PlotStatus.EMPTY);

        when(plotRepository.save(any(PlotEntity.class))).thenReturn(plotEntity);

        PlotDto result = plotService.createPlot(plotDto, 1L);

        assertEquals(1L, result.getId());
        assertEquals(PlotType.PRIVATE, result.getPlotType());
        assertEquals(PlotStatus.EMPTY, result.getPlotStatus());
    }

    @Test
    @DisplayName("PS-005: Should update plot when found")
    public void testUpdatePlotFound() {
        PlotDtoPut plotDto = new PlotDtoPut();
        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(1L);

        when(plotRepository.existsById(1L)).thenReturn(true);
        when(plotRepository.save(any(PlotEntity.class))).thenReturn(plotEntity);

        PlotDto result = plotService.updatePlot(1L, plotDto, 1L);

        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("PS-006: Should throw ResponseStatusException when updating non-existing plot")
    public void testUpdatePlotNotFound() {
        PlotDtoPut plotDto = new PlotDtoPut();
        when(plotRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            plotService.updatePlot(1L, plotDto, 1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertTrue(thrown.getReason().contains("Plot not found with id: 1"));
    }

    @Test
    @DisplayName("PS-007: Should delete existing plot")
    public void testDeletePlotFound() {
        when(plotRepository.findById(1L)).thenReturn(Optional.of(new PlotEntity()));

        plotService.deletePlot(1L, 1L);

        verify(plotRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("PS-008: Should throw ResponseStatusException when deleting non-existing plot")
    public void testDeletePlotNotFound() {
        when(plotRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            plotService.deletePlot(1L, 1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertTrue(thrown.getReason().contains("Plot not found with id: 1"));
    }

    @Test
    @DisplayName("PS-009: Should return plots by block number when found")
    public void testGetPlotsByBlockNumber_Found() {
        BigInteger blockNumber = new BigInteger("100");
        PlotEntity plotEntity1 = new PlotEntity();
        plotEntity1.setId(1L);
        PlotEntity plotEntity2 = new PlotEntity();
        plotEntity2.setId(2L);
        Page<PlotEntity> plotEntities = new PageImpl<>(Arrays.asList(plotEntity1, plotEntity2));

        when(plotRepository.findAllByBlockNumberAndIsActiveTrue(blockNumber, PageRequest.of(1, 10))).thenReturn(plotEntities);

        Page<PlotDto> result = plotService.getPlotsByBlockNumber(blockNumber, PageRequest.of(1, 10));

        assertEquals(2, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals(2L, result.getContent().get(1).getId());
    }

    @Test
    @DisplayName("PS-010: Should throw ResponseStatusException when no plots found for block number")
    public void testGetPlotsByBlockNumber_NotFound() {
        BigInteger blockNumber = new BigInteger("100");
        when(plotRepository.findAllByBlockNumberAndIsActiveTrue(blockNumber, PageRequest.of(1, 10)))
                .thenReturn(Page.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            plotService.getPlotsByBlockNumber(blockNumber, PageRequest.of(1, 10));
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertTrue(thrown.getReason().contains("Plot not found in the block number: " + blockNumber));
    }

    @Test
    @DisplayName("PS-011: Should throw ResponseStatusException for invalid block number")
    public void testGetPlotsByBlockNumber_BadRequest() {
        BigInteger blockNumber = new BigInteger("-1");

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            plotService.getPlotsByBlockNumber(blockNumber, PageRequest.of(1, 10));
        });

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertTrue(thrown.getReason().contains("Block number must be greater than 0."));
    }

    @Test
    @DisplayName("PS-012: Should return plots by status when found")
    public void testGetPlotsByPlotStatus_Found() {
        PlotStatus status = PlotStatus.EMPTY;
        PlotEntity plotEntity1 = new PlotEntity();
        plotEntity1.setId(1L);
        PlotEntity plotEntity2 = new PlotEntity();
        plotEntity2.setId(2L);
        Page<PlotEntity> plotEntities = new PageImpl<>(Arrays.asList(plotEntity1, plotEntity2));

        when(plotRepository.findAllByPlotStatusAndIsActiveTrue(status, PageRequest.of(1, 10)))
                .thenReturn(plotEntities);

        Page<PlotDto> result = plotService.getPlotsByPlotStatus(status, PageRequest.of(1, 10));

        assertEquals(2, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals(2L, result.getContent().get(1).getId());
    }

    @Test
    @DisplayName("PS-013: Should throw ResponseStatusException when no plots found with the given status")
    public void testGetPlotsByPlotStatus_NotFound() {
        PlotStatus status = PlotStatus.EMPTY;
        when(plotRepository.findAllByPlotStatusAndIsActiveTrue(status, PageRequest.of(1, 10)))
                .thenReturn(Page.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            plotService.getPlotsByPlotStatus(status, PageRequest.of(1, 10));
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertTrue(thrown.getReason().contains("Plot not found with the status of: " + status));
    }

    @Test
    @DisplayName("PS-014: Should return plots by type when found")
    public void testGetPlotsByPlotType_Found() {
        PlotType type = PlotType.PRIVATE;
        PlotEntity plotEntity1 = new PlotEntity();
        plotEntity1.setId(1L);
        PlotEntity plotEntity2 = new PlotEntity();
        plotEntity2.setId(2L);
        Page<PlotEntity> plotEntities = new PageImpl<>(Arrays.asList(plotEntity1, plotEntity2));

        when(plotRepository.findAllByPlotTypeAndIsActiveTrue(type, PageRequest.of(1, 10))).thenReturn(plotEntities);

        Page<PlotDto> result = plotService.getPlotsByPlotType(type, PageRequest.of(1, 10));

        assertEquals(2, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals(2L, result.getContent().get(1).getId());
    }

    @Test
    @DisplayName("PS-015: Should throw ResponseStatusException when no plots found with the given type")
    public void testGetPlotsByPlotType_NotFound() {
        PlotType type = PlotType.PRIVATE;
        when(plotRepository.findAllByPlotTypeAndIsActiveTrue(type, PageRequest.of(1, 10)))
                .thenReturn(Page.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            plotService.getPlotsByPlotType(type, PageRequest.of(1, 10));
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertTrue(thrown.getReason().contains("Plot not found with the type of: " + type));
    }

    @Test
    @DisplayName("PS-016: Should assign area to plot when found")
    public void testPlotAssignAreaOverAll_Found() {
        Long plotId = 1L;
        BigDecimal area = new BigDecimal("50.00");
        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(plotId);
        plotEntity.setPercentageForExpense(area);

        when(plotRepository.findById(plotId)).thenReturn(Optional.of(plotEntity));
        when(plotRepository.save(plotEntity)).thenReturn(plotEntity);

        PlotDto result = plotService.plotAssignPercentageForExpense(plotId, area);

        assertEquals(plotId, result.getId());
        assertEquals(area, result.getPercentageForExpense());
    }

    @Test
    @DisplayName("PS-017: Should throw ResponseStatusException when assigning area to non-existing plot")
    public void testPlotAssignAreaOverAll_NotFound() {
        Long plotId = 1L;
        BigDecimal area = new BigDecimal("50.00");

        when(plotRepository.findById(plotId)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            plotService.plotAssignPercentageForExpense(plotId, area);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertTrue(thrown.getReason().contains("Plot not found with id: " + plotId));
    }

    @Test
    @DisplayName("PS-018: Should throw ResponseStatusException for invalid area")
    public void testPlotAssignAreaOverAll_BadRequest() {
        Long plotId = 1L;
        BigDecimal area = new BigDecimal("-10.00");

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            plotService.plotAssignPercentageForExpense(plotId, area);
        });

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertTrue(thrown.getReason().contains("Area number must be greater than 0.00."));
    }
}
