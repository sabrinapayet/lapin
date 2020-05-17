/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import LapinComponent from '@/entities/lapin/lapin.vue';
import LapinClass from '@/entities/lapin/lapin.component';
import LapinService from '@/entities/lapin/lapin.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Lapin Management Component', () => {
    let wrapper: Wrapper<LapinClass>;
    let comp: LapinClass;
    let lapinServiceStub: SinonStubbedInstance<LapinService>;

    beforeEach(() => {
      lapinServiceStub = sinon.createStubInstance<LapinService>(LapinService);
      lapinServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LapinClass>(LapinComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          lapinService: () => lapinServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      lapinServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLapins();
      await comp.$nextTick();

      // THEN
      expect(lapinServiceStub.retrieve.called).toBeTruthy();
      expect(comp.lapins[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      lapinServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeLapin();
      await comp.$nextTick();

      // THEN
      expect(lapinServiceStub.delete.called).toBeTruthy();
      expect(lapinServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
