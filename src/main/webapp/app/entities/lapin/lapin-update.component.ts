import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import { ILapin, Lapin } from '@/shared/model/lapin.model';
import LapinService from './lapin.service';

const validations: any = {
  lapin: {
    created: {},
    modify: {},
    deleted: {},
  },
};

@Component({
  validations,
})
export default class LapinUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('lapinService') private lapinService: () => LapinService;
  public lapin: ILapin = new Lapin();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.lapinId) {
        vm.retrieveLapin(to.params.lapinId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.lapin.id) {
      this.lapinService()
        .update(this.lapin)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('lapin5App.lapin.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.lapinService()
        .create(this.lapin)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('lapin5App.lapin.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.lapin[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.lapin[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.lapin[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.lapin[field] = null;
    }
  }

  public retrieveLapin(lapinId): void {
    this.lapinService()
      .find(lapinId)
      .then(res => {
        res.modify = new Date(res.modify);
        res.deleted = new Date(res.deleted);
        this.lapin = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
