<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="lapin5App.lapin.home.createOrEditLabel" v-text="$t('lapin5App.lapin.home.createOrEditLabel')">Create or edit a Lapin</h2>
        <div>
          <div class="form-group" v-if="lapin.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="lapin.id" readonly />
          </div>
          <div>
            <label v-text="$t('lapin5App.lapin.created')" for="lapin-created">Created</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="lapin-created"
                  v-model="$v.lapin.created.$model"
                  name="created"
                  :start-weekday="1"
                  class="mb-2"
                  :locale="currentLanguage"
                  button-only
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="lapin-created"
                v-model="$v.lapin.created.$model"
                type="text"
                placeholder="YYYY-MM-DD"
                autocomplete="off"
              ></b-form-input>
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('lapin5App.lapin.modify')" for="lapin-modify">Modify</label>
            <div class="d-flex">
              <input
                id="lapin-modify"
                type="datetime-local"
                class="form-control"
                name="modify"
                :class="{ valid: !$v.lapin.modify.$invalid, invalid: $v.lapin.modify.$invalid }"
                :value="convertDateTimeFromServer($v.lapin.modify.$model)"
                @change="updateInstantField('modify', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('lapin5App.lapin.deleted')" for="lapin-deleted">Deleted</label>
            <div class="d-flex">
              <input
                id="lapin-deleted"
                type="datetime-local"
                class="form-control"
                name="deleted"
                :class="{ valid: !$v.lapin.deleted.$invalid, invalid: $v.lapin.deleted.$invalid }"
                :value="convertDateTimeFromServer($v.lapin.deleted.$model)"
                @change="updateZonedDateTimeField('deleted', $event)"
              />
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button type="submit" id="save-entity" :disabled="$v.lapin.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./lapin-update.component.ts"></script>
