# Testear metodo privado

<p>
    <em>Para testear un metodo privado vamos a usar la clase ReflectionTestUtils, con el metodo invokeMethod.</em>
</p>
<em>Metodo privado:</em>

```java
    private void calculateMiscellaneous(TeamStanding team, TeamResultsDto teamResult) {
        team.setPointsDifferential(team.getPointsFor() - team.getPointsAgainst());
        team.setTotalYellowCards(team.getTotalYellowCards() + teamResult.getYellowCards());
        team.setTotalRedCards(team.getTotalRedCards() + teamResult.getRedCards());
        team.setMatchesPlayed(team.getMatchesPlayed() + 1);
        team.setPoints(team.getWins() * 4 + team.getDraws() + team.getBonusPoints());
    }
```

<em>Test:</em>

```java
    @Test
    void calculateMiscellaneous() {
        TeamStanding teamStanding = new TeamStanding();
        teamStanding.setPointsFor(100);
        teamStanding.setPointsAgainst(25);
        teamStanding.setTotalYellowCards(100);
        teamStanding.setTotalRedCards(100);
        teamStanding.setMatchesPlayed(100);
        teamStanding.setWins(10);
        teamStanding.setDraws(5);
        teamStanding.setBonusPoints(5);

        TeamResultsDto teamResultsDto = new TeamResultsDto();
        teamResultsDto.setYellowCards(50);
        teamResultsDto.setRedCards(50);

        ReflectionTestUtils.invokeMethod(poolService, "calculateMiscellaneous", teamStanding, teamResultsDto);

        assertEquals(50, teamStanding.getPoints());
        assertEquals(150, teamStanding.getTotalYellowCards());
        assertEquals(150, teamStanding.getTotalRedCards());
        assertEquals(75, teamStanding.getPointsDifferential());
        assertEquals(101, teamStanding.getMatchesPlayed());
    }
```

# Explicacion 

<em>En este caso como el metodo privado es **void**, no devolvera nada, en caso de que tu metodo privado devuelva algo, el metodo de invokeMethod devuelve un generico, asi que vas a poder atraparlo, cualquiera sea la variable que devuelve tu metodo privado.</em>
<br><br>
<em>El metodo invokedMethod te pedira la instancia de la clase que deseas testear, el nombre del metodo privado y sus parametros, como si llamaras a la funcion normal.</em>