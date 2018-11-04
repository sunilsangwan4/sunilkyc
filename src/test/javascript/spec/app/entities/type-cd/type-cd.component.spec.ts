/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { TypeCdComponent } from 'app/entities/type-cd/type-cd.component';
import { TypeCdService } from 'app/entities/type-cd/type-cd.service';
import { TypeCd } from 'app/shared/model/type-cd.model';

describe('Component Tests', () => {
    describe('TypeCd Management Component', () => {
        let comp: TypeCdComponent;
        let fixture: ComponentFixture<TypeCdComponent>;
        let service: TypeCdService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TypeCdComponent],
                providers: []
            })
                .overrideTemplate(TypeCdComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeCdComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCdService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TypeCd(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.typeCds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
