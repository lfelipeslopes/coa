/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { VehicleAccountDetailComponent } from 'app/entities/vehicle-account/vehicle-account-detail.component';
import { VehicleAccount } from 'app/shared/model/vehicle-account.model';

describe('Component Tests', () => {
    describe('VehicleAccount Management Detail Component', () => {
        let comp: VehicleAccountDetailComponent;
        let fixture: ComponentFixture<VehicleAccountDetailComponent>;
        const route = ({ data: of({ vehicleAccount: new VehicleAccount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [VehicleAccountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VehicleAccountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VehicleAccountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vehicleAccount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
