'use babel';

import MyPackageView from './my-package-view';
import { CompositeDisposable } from 'atom';

export default {

  myPackageView: null,
  modalPanel: null,
  subscriptions: null,

  activate(state) {
    this.myPackageView = new MyPackageView(state.myPackageViewState);
    this.modalPanel = atom.workspace.addModalPanel({
      item: this.myPackageView.getElement(),
      visible: false
    });

    // Events subscribed to in atom's system can be easily cleaned up with a CompositeDisposable
    this.subscriptions = new CompositeDisposable();

    // Register command that toggles this view
    this.subscriptions.add(atom.commands.add('atom-workspace', {
      'my-package:toggle': () => this.toggle()
    }));
  },

  deactivate() {
    this.modalPanel.destroy();
    this.subscriptions.dispose();
    this.myPackageView.destroy();
  },

  serialize() {
    return {
      myPackageViewState: this.myPackageView.serialize()
    };
  },

  toggle() {
  let editor
  var beginning = '$teste=("'
  var ending = '")'

  //*************************************************
  //Only testing here


  //*************************************************


  if (editor = atom.workspace.getActiveTextEditor()) {
    let selection = editor.getSelectedText()

    var quebrar = selection.split(',')
    var reversed
    var final
    var finalissimo = []

    for(i = 0; i < quebrar.length; i++){
        reversed = quebrar[i].split('')

        reversed.push(ending)
        reversed.unshift(beginning)

        if(i != quebrar.length - 1)
            reversed.push(',')

        console.log(reversed)

        final = reversed.join('')
        finalissimo.push(final)

        console.log(finalissimo)

        //editor.insertText(final)
    }
        editor.insertText(finalissimo.join(''))
  }
}

};
