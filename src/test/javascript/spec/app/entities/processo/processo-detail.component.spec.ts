/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DiscoveryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProcessoDetailComponent } from '../../../../../../main/webapp/app/entities/processo/processo-detail.component';
import { ProcessoService } from '../../../../../../main/webapp/app/entities/processo/processo.service';
import { Processo } from '../../../../../../main/webapp/app/entities/processo/processo.model';

describe('Component Tests', () => {

    describe('Processo Management Detail Component', () => {
        let comp: ProcessoDetailComponent;
        let fixture: ComponentFixture<ProcessoDetailComponent>;
        let service: ProcessoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DiscoveryTestModule],
                declarations: [ProcessoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProcessoService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProcessoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcessoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcessoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Processo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.processo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
