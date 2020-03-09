import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { PublicacaoFeedUpdateComponent } from 'app/entities/sgq/publicacao-feed/publicacao-feed-update.component';
import { PublicacaoFeedService } from 'app/entities/sgq/publicacao-feed/publicacao-feed.service';
import { PublicacaoFeed } from 'app/shared/model/sgq/publicacao-feed.model';

describe('Component Tests', () => {
  describe('PublicacaoFeed Management Update Component', () => {
    let comp: PublicacaoFeedUpdateComponent;
    let fixture: ComponentFixture<PublicacaoFeedUpdateComponent>;
    let service: PublicacaoFeedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [PublicacaoFeedUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PublicacaoFeedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PublicacaoFeedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PublicacaoFeedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PublicacaoFeed(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PublicacaoFeed();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
