/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { GroupCdComponent } from 'app/entities/group-cd/group-cd.component';
import { GroupCdService } from 'app/entities/group-cd/group-cd.service';
import { GroupCd } from 'app/shared/model/group-cd.model';

describe('Component Tests', () => {
    describe('GroupCd Management Component', () => {
        let comp: GroupCdComponent;
        let fixture: ComponentFixture<GroupCdComponent>;
        let service: GroupCdService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [GroupCdComponent],
                providers: []
            })
                .overrideTemplate(GroupCdComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GroupCdComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupCdService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new GroupCd(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.groupCds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
