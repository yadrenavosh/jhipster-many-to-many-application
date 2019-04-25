/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ExternalCodeComponentsPage, ExternalCodeDeleteDialog, ExternalCodeUpdatePage } from './external-code.page-object';

const expect = chai.expect;

describe('ExternalCode e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let externalCodeUpdatePage: ExternalCodeUpdatePage;
    let externalCodeComponentsPage: ExternalCodeComponentsPage;
    let externalCodeDeleteDialog: ExternalCodeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ExternalCodes', async () => {
        await navBarPage.goToEntity('external-code');
        externalCodeComponentsPage = new ExternalCodeComponentsPage();
        await browser.wait(ec.visibilityOf(externalCodeComponentsPage.title), 5000);
        expect(await externalCodeComponentsPage.getTitle()).to.eq('External Codes');
    });

    it('should load create ExternalCode page', async () => {
        await externalCodeComponentsPage.clickOnCreateButton();
        externalCodeUpdatePage = new ExternalCodeUpdatePage();
        expect(await externalCodeUpdatePage.getPageTitle()).to.eq('Create or edit a External Code');
        await externalCodeUpdatePage.cancel();
    });

    it('should create and save ExternalCodes', async () => {
        const nbButtonsBeforeCreate = await externalCodeComponentsPage.countDeleteButtons();

        await externalCodeComponentsPage.clickOnCreateButton();
        await promise.all([externalCodeUpdatePage.setCodeInput('code'), externalCodeUpdatePage.setDescriptionInput('description')]);
        expect(await externalCodeUpdatePage.getCodeInput()).to.eq('code');
        expect(await externalCodeUpdatePage.getDescriptionInput()).to.eq('description');
        await externalCodeUpdatePage.save();
        expect(await externalCodeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await externalCodeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ExternalCode', async () => {
        const nbButtonsBeforeDelete = await externalCodeComponentsPage.countDeleteButtons();
        await externalCodeComponentsPage.clickOnLastDeleteButton();

        externalCodeDeleteDialog = new ExternalCodeDeleteDialog();
        expect(await externalCodeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this External Code?');
        await externalCodeDeleteDialog.clickOnConfirmButton();

        expect(await externalCodeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
