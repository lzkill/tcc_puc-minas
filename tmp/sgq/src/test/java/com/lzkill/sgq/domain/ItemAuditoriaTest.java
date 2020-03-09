package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class ItemAuditoriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemAuditoria.class);
        ItemAuditoria itemAuditoria1 = new ItemAuditoria();
        itemAuditoria1.setId(1L);
        ItemAuditoria itemAuditoria2 = new ItemAuditoria();
        itemAuditoria2.setId(itemAuditoria1.getId());
        assertThat(itemAuditoria1).isEqualTo(itemAuditoria2);
        itemAuditoria2.setId(2L);
        assertThat(itemAuditoria1).isNotEqualTo(itemAuditoria2);
        itemAuditoria1.setId(null);
        assertThat(itemAuditoria1).isNotEqualTo(itemAuditoria2);
    }
}
