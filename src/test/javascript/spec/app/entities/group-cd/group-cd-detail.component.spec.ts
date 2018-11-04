/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { GroupCdDetailComponent } from 'app/entities/group-cd/group-cd-detail.component';
import { GroupCd } from 'app/shared/model/group-cd.model';

describe('Component Tests', () => {
    describe('GroupCd Management Detail Component', () => {
        let comp: GroupCdDetailComponent;
        let fixture: ComponentFixture<GroupCdDetailComponent>;
        const route = ({ data: of({ groupCd: new GroupCd(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [GroupCdDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GroupCdDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GroupCdDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.groupCd).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
