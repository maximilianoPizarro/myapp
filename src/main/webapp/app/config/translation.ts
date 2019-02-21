import { TranslatorContext, Storage } from 'react-jhipster';

import { setLocale } from 'app/shared/reducers/locale';

TranslatorContext.setDefaultLocale('es');
TranslatorContext.setRenderInnerTextForMissingKeys(false);

export const languages: any = {
  sr: { name: 'Srpski' },
  es: { name: 'Español' }
  // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
};

export const locales = Object.keys(languages).sort();

export const registerLocale = store => {
  store.dispatch(setLocale(Storage.session.get('locale', 'es')));
};
