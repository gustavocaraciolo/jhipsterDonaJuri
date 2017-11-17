/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DiscoveryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EscritorioDetailComponent } from '../../../../../../main/webapp/app/entities/escritorio/escritorio-detail.component';
import { EscritorioService } from '../../../../../../main/webapp/app/entities/escritorio/escritorio.service';
import { Escritorio } from '../../../../../../main/webapp/app/entities/escritorio/escritorio.model';

describe('Component Tests', () => {

    describe('Escritorio Management Detail Component', () => {
        let comp: EscritorioDetailComponent;
        let fixture: ComponentFixture<EscritorioDetailComponent>;
        let service: EscritorioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DiscoveryTestModule],
                declarations: [EscritorioDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EscritorioService,
                    JhiEventManager
                ]
            }).overrideTemplate(EscritorioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EscritorioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EscritorioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Escritorio(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.escritorio).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
