import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { FeedUpdateComponent } from 'app/entities/sgq/feed/feed-update.component';
import { FeedService } from 'app/entities/sgq/feed/feed.service';
import { Feed } from 'app/shared/model/sgq/feed.model';

describe('Component Tests', () => {
  describe('Feed Management Update Component', () => {
    let comp: FeedUpdateComponent;
    let fixture: ComponentFixture<FeedUpdateComponent>;
    let service: FeedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FeedUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FeedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FeedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Feed(123);
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
        const entity = new Feed();
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
