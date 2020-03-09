import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { FeedDetailComponent } from 'app/entities/sgq/feed/feed-detail.component';
import { Feed } from 'app/shared/model/sgq/feed.model';

describe('Component Tests', () => {
  describe('Feed Management Detail Component', () => {
    let comp: FeedDetailComponent;
    let fixture: ComponentFixture<FeedDetailComponent>;
    const route = ({ data: of({ feed: new Feed(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [FeedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FeedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load feed on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.feed).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
