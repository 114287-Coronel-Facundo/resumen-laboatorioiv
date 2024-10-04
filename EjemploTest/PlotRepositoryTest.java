@DataJpaTest
public class PlotRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @SpyBean
    private PlotRepository plotRepository;

    private Page<PlotEntity> resultPlotEntityList;

    @BeforeEach
    void dummyData() {
        List<PlotEntity> plotEntityList = new ArrayList<>();

        // Match Plot Result #1
        PlotEntity plotEntity1 = new PlotEntity();
        plotEntity1.setBlockNumber(new BigInteger("1"));
        plotEntity1.setPlotStatus(PlotStatus.EMPTY);
        plotEntity1.setPlotType(PlotType.PRIVATE);
        plotEntity1.setIsActive(true);
        plotEntityList.add(plotEntity1);

        // Match Plot Result #2
        PlotEntity plotEntity2 = new PlotEntity();
        plotEntity2.setBlockNumber(new BigInteger("1"));
        plotEntity2.setPlotStatus(PlotStatus.EMPTY);
        plotEntity2.setPlotType(PlotType.PRIVATE);
        plotEntity2.setIsActive(true);
        plotEntityList.add(plotEntity2);

        // Match Plot Result But Deleted #3
        PlotEntity plotEntity3 = new PlotEntity();
        plotEntity3.setBlockNumber(new BigInteger("1"));
        plotEntity3.setPlotStatus(PlotStatus.EMPTY);
        plotEntity3.setPlotType(PlotType.PRIVATE);
        plotEntity3.setIsActive(false);
        plotEntityList.add(plotEntity3);

        // Not-Match Plot Result #4
        PlotEntity plotEntity4 = new PlotEntity();
        plotEntity4.setBlockNumber(new BigInteger("2"));
        plotEntity4.setPlotStatus(PlotStatus.SALE);
        plotEntity4.setPlotType(PlotType.COMMERCIAL);
        plotEntity4.setIsActive(true);
        plotEntityList.add(plotEntity4);

        plotEntityList.forEach(entity -> entityManager.persistAndFlush(entity));
    }

    @Test
    void findAllByBlockNumberAndIsActiveTrueTest() {
        resultPlotEntityList = plotRepository.findAllByBlockNumberAndIsActiveTrue(new BigInteger("1"), PageRequest.of(0, 10));

        assertEquals(2, resultPlotEntityList.getTotalElements());
        assertEquals(new BigInteger("1"), resultPlotEntityList.getContent().get(0).getBlockNumber());
        assertEquals(new BigInteger("1"), resultPlotEntityList.getContent().get(1).getBlockNumber());
    }

    @Test
    void findAllByPlotStatusAndIsActiveTrueTest() {
        resultPlotEntityList = plotRepository.findAllByPlotStatusAndIsActiveTrue(PlotStatus.EMPTY, PageRequest.of(0, 10));

        assertEquals(2, resultPlotEntityList.getTotalElements());
        assertEquals(PlotStatus.EMPTY, resultPlotEntityList.getContent().get(0).getPlotStatus());
        assertEquals(PlotStatus.EMPTY, resultPlotEntityList.getContent().get(1).getPlotStatus());
    }

    @Test
    void findAllByPlotTypeAndIsActiveTrueTest() {
        resultPlotEntityList = plotRepository.findAllByPlotTypeAndIsActiveTrue(PlotType.PRIVATE, PageRequest.of(0, 10));

        assertEquals(2, resultPlotEntityList.getTotalElements());
        assertEquals(PlotType.PRIVATE, resultPlotEntityList.getContent().get(0).getPlotType());
        assertEquals(PlotType.PRIVATE, resultPlotEntityList.getContent().get(1).getPlotType());
    }

    @Test
    void findAllByIsActiveTrue() {
        resultPlotEntityList = plotRepository.findAllByIsActiveTrue(PageRequest.of(0, 10));

        assertEquals(3, resultPlotEntityList.getTotalElements());
        assertTrue(resultPlotEntityList.getContent().get(0).getIsActive());
        assertTrue(resultPlotEntityList.getContent().get(1).getIsActive());
        assertTrue(resultPlotEntityList.getContent().get(2).getIsActive());
    }
}
