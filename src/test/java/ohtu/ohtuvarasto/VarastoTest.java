package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto, varastoTilavuusNolla, varastoSaldoYksi, varastoSaldoLiikaa, varastoSaldoYliTilavuuden, varastoSaldoNegatiivinen;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
        varastoTilavuusNolla = new Varasto(0);
        varastoSaldoYksi = new Varasto(10, 1);
        varastoSaldoLiikaa = new Varasto(10, 11);
        varastoSaldoYliTilavuuden = new Varasto(0, 10);
        varastoSaldoNegatiivinen = new Varasto(10, -1);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(5, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void lisaaNegatiivinen() {
        double saldoEnnen = varasto.getSaldo();
        varasto.lisaaVarastoon(-4);

        // saldon pitäisi olla sama
        assertEquals(saldoEnnen, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void otaNegatiivinen() {
        double saldoEnnen = varasto.getSaldo();
        varasto.otaVarastosta(-4);

        // saldon pitäisi olla sama
        assertEquals(saldoEnnen, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaaYliTilavuuden() {
        varasto.lisaaVarastoon(varasto.getTilavuus() + 1);

        // saldon pitäisi olla maksimitilavuus
        assertEquals(varasto.getSaldo(), varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void otaEnemmanKuinSaldo() {
        double saldoEnnen = varasto.getSaldo();
        double otettuSaldo = varasto.otaVarastosta(saldoEnnen + 1);

        // saldon pitäisi olla 0
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
        //
        assertEquals(otettuSaldo, saldoEnnen, vertailuTarkkuus);
    }

    @Test
    public void merkkijonoesitysOikein() {
        assertEquals("saldo = 0.0, vielä tilaa 10.0", varasto.toString());
    }
}