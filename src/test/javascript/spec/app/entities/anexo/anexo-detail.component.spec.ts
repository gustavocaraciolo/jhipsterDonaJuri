/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DiscoveryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AnexoDetailComponent } from '../../../../../../main/webapp/app/entities/anexo/anexo-detail.component';
import { AnexoService } from '../../../../../../main/webapp/app/entities/anexo/anexo.service';
import { Anexo } from '../../../../../../main/webapp/app/entities/anexo/anexo.model';

describe('Component Tests', () => {

    describe('Anexo Management Detail Component', () => {
        let comp: AnexoDetailComponent;
        let fixture: ComponentFixture<AnexoDetailComponent>;
        let service: AnexoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DiscoveryTestModule],
                declarations: [AnexoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AnexoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AnexoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnexoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnexoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Anexo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.anexo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
