/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LapinDetailComponent from '@/entities/lapin/lapin-details.vue';
import LapinClass from '@/entities/lapin/lapin-details.component';
import LapinService from '@/entities/lapin/lapin.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Lapin Management Detail Component', () => {
    let wrapper: Wrapper<LapinClass>;
    let comp: LapinClass;
    let lapinServiceStub: SinonStubbedInstance<LapinService>;

    beforeEach(() => {
      lapinServiceStub = sinon.createStubInstance<LapinService>(LapinService);

      wrapper = shallowMount<LapinClass>(LapinDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { lapinService: () => lapinServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLapin = { id: 123 };
        lapinServiceStub.find.resolves(foundLapin);

        // WHEN
        comp.retrieveLapin(123);
        await comp.$nextTick();

        // THEN
        expect(comp.lapin).toBe(foundLapin);
      });
    });
  });
});
