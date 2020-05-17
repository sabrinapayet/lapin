import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILapin } from '@/shared/model/lapin.model';
import LapinService from './lapin.service';

@Component
export default class LapinDetails extends Vue {
  @Inject('lapinService') private lapinService: () => LapinService;
  public lapin: ILapin = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.lapinId) {
        vm.retrieveLapin(to.params.lapinId);
      }
    });
  }

  public retrieveLapin(lapinId) {
    this.lapinService()
      .find(lapinId)
      .then(res => {
        this.lapin = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
