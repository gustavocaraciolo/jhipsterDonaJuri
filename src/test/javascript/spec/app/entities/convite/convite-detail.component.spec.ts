/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DiscoveryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConviteDetailComponent } from '../../../../../../main/webapp/app/entities/convite/convite-detail.component';
import { ConviteService } from '../../../../../../main/webapp/app/entities/convite/convite.service';
import { Convite } from '../../../../../../main/webapp/app/entities/convite/convite.model';

describe('Component Tests', () => {

    describe('Convite Management Detail Component', () => {
        let comp: ConviteDetailComponent;
        let fixture: ComponentFixture<ConviteDetailComponent>;
        let service: ConviteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DiscoveryTestModule],
                declarations: [ConviteDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConviteService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConviteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConviteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConviteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Convite(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.convite).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
