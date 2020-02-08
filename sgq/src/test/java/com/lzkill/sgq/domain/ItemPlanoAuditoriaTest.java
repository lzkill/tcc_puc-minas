package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class ItemPlanoAuditoriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPlanoAuditoria.class);
        ItemPlanoAuditoria itemPlanoAuditoria1 = new ItemPlanoAuditoria();
        itemPlanoAuditoria1.setId(1L);
        ItemPlanoAuditoria itemPlanoAuditoria2 = new ItemPlanoAuditoria();
        itemPlanoAuditoria2.setId(itemPlanoAuditoria1.getId());
        assertThat(itemPlanoAuditoria1).isEqualTo(itemPlanoAuditoria2);
        itemPlanoAuditoria2.setId(2L);
        assertThat(itemPlanoAuditoria1).isNotEqualTo(itemPlanoAuditoria2);
        itemPlanoAuditoria1.setId(null);
        assertThat(itemPlanoAuditoria1).isNotEqualTo(itemPlanoAuditoria2);
    }
}
