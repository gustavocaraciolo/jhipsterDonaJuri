/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DiscoveryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PendenciaDetailComponent } from '../../../../../../main/webapp/app/entities/pendencia/pendencia-detail.component';
import { PendenciaService } from '../../../../../../main/webapp/app/entities/pendencia/pendencia.service';
import { Pendencia } from '../../../../../../main/webapp/app/entities/pendencia/pendencia.model';

describe('Component Tests', () => {

    describe('Pendencia Management Detail Component', () => {
        let comp: PendenciaDetailComponent;
        let fixture: ComponentFixture<PendenciaDetailComponent>;
        let service: PendenciaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DiscoveryTestModule],
                declarations: [PendenciaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PendenciaService,
                    JhiEventManager
                ]
            }).overrideTemplate(PendenciaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PendenciaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PendenciaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pendencia(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pendencia).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
