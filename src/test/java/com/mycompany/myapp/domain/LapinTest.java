package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LapinTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lapin.class);
        Lapin lapin1 = new Lapin();
        lapin1.setId(1L);
        Lapin lapin2 = new Lapin();
        lapin2.setId(lapin1.getId());
        assertThat(lapin1).isEqualTo(lapin2);
        lapin2.setId(2L);
        assertThat(lapin1).isNotEqualTo(lapin2);
        lapin1.setId(null);
        assertThat(lapin1).isNotEqualTo(lapin2);
    }
}
