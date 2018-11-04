import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IApplicationProspect } from 'app/shared/model/application-prospect.model';
import { ApplicationProspectService } from './application-prospect.service';
import { IPersonalInformation } from 'app/shared/model/personal-information.model';
import { PersonalInformationService } from 'app/entities/personal-information';
import { IInvestmentPotential } from 'app/shared/model/investment-potential.model';
import { InvestmentPotentialService } from 'app/entities/investment-potential';
import { INominee } from 'app/shared/model/nominee.model';
import { NomineeService } from 'app/entities/nominee';
import { ITradingInfo } from 'app/shared/model/trading-info.model';
import { TradingInfoService } from 'app/entities/trading-info';
import { IDepositoryInfo } from 'app/shared/model/depository-info.model';
import { DepositoryInfoService } from 'app/entities/depository-info';

@Component({
    selector: 'jhi-application-prospect-update',
    templateUrl: './application-prospect-update.component.html'
})
export class ApplicationProspectUpdateComponent implements OnInit {
    applicationProspect: IApplicationProspect;
    isSaving: boolean;
    loggedIn: boolean;
    otpSent: boolean;
    verified: boolean;
    otp: number;
    filledOTP: number;

    personalinformations: IPersonalInformation[];

    investmentpotentials: IInvestmentPotential[];

    nominees: INominee[];

    tradinginfos: ITradingInfo[];

    depositories: IDepositoryInfo[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private applicationProspectService: ApplicationProspectService,
        private personalInformationService: PersonalInformationService,
        private investmentPotentialService: InvestmentPotentialService,
        private nomineeService: NomineeService,
        private tradingInfoService: TradingInfoService,
        private depositoryInfoService: DepositoryInfoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.loggedIn = false;
        this.otpSent = false;
        this.verified = false;
        this.otp = 222222;
        this.activatedRoute.data.subscribe(({ applicationProspect }) => {
            this.applicationProspect = applicationProspect;
        });

        if (this.loggedIn) {
            this.personalInformationService.query({ 'applicationProspectId.specified': 'false' }).subscribe(
                (res: HttpResponse<IPersonalInformation[]>) => {
                    if (!this.applicationProspect.personalInformationId) {
                        this.personalinformations = res.body;
                    } else {
                        this.personalInformationService.find(this.applicationProspect.personalInformationId).subscribe(
                            (subRes: HttpResponse<IPersonalInformation>) => {
                                this.personalinformations = [subRes.body].concat(res.body);
                            },
                            (subRes: HttpErrorResponse) => this.onError(subRes.message)
                        );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            this.investmentPotentialService.query({ 'applicationProspectId.specified': 'false' }).subscribe(
                (res: HttpResponse<IInvestmentPotential[]>) => {
                    if (!this.applicationProspect.investmentPotentialId) {
                        this.investmentpotentials = res.body;
                    } else {
                        this.investmentPotentialService.find(this.applicationProspect.investmentPotentialId).subscribe(
                            (subRes: HttpResponse<IInvestmentPotential>) => {
                                this.investmentpotentials = [subRes.body].concat(res.body);
                            },
                            (subRes: HttpErrorResponse) => this.onError(subRes.message)
                        );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            this.nomineeService.query({ 'applicationProspectId.specified': 'false' }).subscribe(
                (res: HttpResponse<INominee[]>) => {
                    if (!this.applicationProspect.nomineeId) {
                        this.nominees = res.body;
                    } else {
                        this.nomineeService.find(this.applicationProspect.nomineeId).subscribe(
                            (subRes: HttpResponse<INominee>) => {
                                this.nominees = [subRes.body].concat(res.body);
                            },
                            (subRes: HttpErrorResponse) => this.onError(subRes.message)
                        );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            this.tradingInfoService.query({ 'applicationProspectId.specified': 'false' }).subscribe(
                (res: HttpResponse<ITradingInfo[]>) => {
                    if (!this.applicationProspect.tradingInfoId) {
                        this.tradinginfos = res.body;
                    } else {
                        this.tradingInfoService.find(this.applicationProspect.tradingInfoId).subscribe(
                            (subRes: HttpResponse<ITradingInfo>) => {
                                this.tradinginfos = [subRes.body].concat(res.body);
                            },
                            (subRes: HttpErrorResponse) => this.onError(subRes.message)
                        );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            this.depositoryInfoService.query({ 'applicationProspectId.specified': 'false' }).subscribe(
                (res: HttpResponse<IDepositoryInfo[]>) => {
                    if (!this.applicationProspect.depositoryId) {
                        this.depositories = res.body;
                    } else {
                        this.depositoryInfoService.find(this.applicationProspect.depositoryId).subscribe(
                            (subRes: HttpResponse<IDepositoryInfo>) => {
                                this.depositories = [subRes.body].concat(res.body);
                            },
                            (subRes: HttpErrorResponse) => this.onError(subRes.message)
                        );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
    }

    sendOTP() {
        this.otpSent = true;
    }

    verifyOTP() {
        if (this.otp == this.filledOTP) {
            this.verified = true;
        }
    }
    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.applicationProspect.id !== undefined) {
            this.subscribeToSaveResponse(this.applicationProspectService.update(this.applicationProspect));
        } else {
            this.subscribeToSaveResponse(this.applicationProspectService.create(this.applicationProspect));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationProspect>>) {
        result.subscribe((res: HttpResponse<IApplicationProspect>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPersonalInformationById(index: number, item: IPersonalInformation) {
        return item.id;
    }

    trackInvestmentPotentialById(index: number, item: IInvestmentPotential) {
        return item.id;
    }

    trackNomineeById(index: number, item: INominee) {
        return item.id;
    }

    trackTradingInfoById(index: number, item: ITradingInfo) {
        return item.id;
    }

    trackDepositoryInfoById(index: number, item: IDepositoryInfo) {
        return item.id;
    }
}
