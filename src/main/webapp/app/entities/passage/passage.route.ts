import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Passage } from 'app/shared/model/passage.model';
import { PassageService } from './passage.service';
import { PassageComponent } from './passage.component';
import { PassageDetailComponent } from './passage-detail.component';
import { PassageUpdateComponent } from './passage-update.component';
import { PassageDeletePopupComponent } from './passage-delete-dialog.component';
import { IPassage } from 'app/shared/model/passage.model';

@Injectable({ providedIn: 'root' })
export class PassageResolve implements Resolve<IPassage> {
    constructor(private service: PassageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPassage> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Passage>) => response.ok),
                map((passage: HttpResponse<Passage>) => passage.body)
            );
        }
        return of(new Passage());
    }
}

export const passageRoute: Routes = [
    {
        path: '',
        component: PassageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PassageDetailComponent,
        resolve: {
            passage: PassageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PassageUpdateComponent,
        resolve: {
            passage: PassageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PassageUpdateComponent,
        resolve: {
            passage: PassageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const passagePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PassageDeletePopupComponent,
        resolve: {
            passage: PassageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Passages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
