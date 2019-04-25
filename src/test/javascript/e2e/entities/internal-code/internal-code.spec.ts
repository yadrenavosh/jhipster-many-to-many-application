/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InternalCodeComponentsPage, InternalCodeDeleteDialog, InternalCodeUpdatePage } from './internal-code.page-object';

const expect = chai.expect;

describe('InternalCode e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let internalCodeUpdatePage: InternalCodeUpdatePage;
    let internalCodeComponentsPage: InternalCodeComponentsPage;
    let internalCodeDeleteDialog: InternalCodeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load InternalCodes', async () => {
        await navBarPage.goToEntity('internal-code');
        internalCodeComponentsPage = new InternalCodeComponentsPage();
        await browser.wait(ec.visibilityOf(internalCodeComponentsPage.title), 5000);
        expect(await internalCodeComponentsPage.getTitle()).to.eq('Internal Codes');
    });

    it('should load create InternalCode page', async () => {
        await internalCodeComponentsPage.clickOnCreateButton();
        internalCodeUpdatePage = new InternalCodeUpdatePage();
        expect(await internalCodeUpdatePage.getPageTitle()).to.eq('Create or edit a Internal Code');
        await internalCodeUpdatePage.cancel();
    });

    it('should create and save InternalCodes', async () => {
        const nbButtonsBeforeCreate = await internalCodeComponentsPage.countDeleteButtons();

        await internalCodeComponentsPage.clickOnCreateButton();
        await promise.all([
            internalCodeUpdatePage.setCodeInput('code'),
            internalCodeUpdatePage.setDescriptionInput('description')
            // internalCodeUpdatePage.externalSelectLastOption(),
        ]);
        expect(await internalCodeUpdatePage.getCodeInput()).to.eq('code');
        expect(await internalCodeUpdatePage.getDescriptionInput()).to.eq('description');
        await internalCodeUpdatePage.save();
        expect(await internalCodeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await internalCodeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last InternalCode', async () => {
        const nbButtonsBeforeDelete = await internalCodeComponentsPage.countDeleteButtons();
        await internalCodeComponentsPage.clickOnLastDeleteButton();

        internalCodeDeleteDialog = new InternalCodeDeleteDialog();
        expect(await internalCodeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Internal Code?');
        await internalCodeDeleteDialog.clickOnConfirmButton();

        expect(await internalCodeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
