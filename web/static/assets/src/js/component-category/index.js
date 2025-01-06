const heading = document.querySelector('#modal-title');

class Page extends CRUDPage
{
    constructor(btnAdd, table, form)
    { super('/api/misc/component-category', btnAdd, table, form); }

    init()
    {
        super.init();
        this.btnAdd.onclick = () => {
            this.form.reset();
            this.form.onsubmit(this.create.bind(this))
            
            heading.textContent = 'Insertion Catégorie de Pièce';
        };
    }

    render(data)
    {
        const tbody = table.querySelector('tbody');
        tbody.replaceChildren();

        for(const row of data.page)
        {
            tbody.append(tag('tr', {}, [
                tag('td', {'class': 'd-flex gap-1'}, [
                    tag('button',
                        {
                            'class': 'btn btn-secondary text-info',
                            'data-mdb-ripple-init': true,
                            'data-mdb-modal-init': true,
                            'data-mdb-target': '#modal',
                            'onclick': () => {
                                heading.textContent = 'Modification Catégorie de Pièce';
                                this.form.load({
                                    'label': row.label,
                                });
                                this.form.onsubmit(this.update.bind(this, row.id));
                            }
                        },
                        [icon({}, ['fa', 'fa-pencil'])]
                    ),
                    tag('button', {'class': 'btn btn-secondary text-danger','onclick': this.delete.bind(this, row.id)}, [icon({}, ['fa', 'fa-trash'])]),
                ]),
                tag('td', {}, [text(row.label)]),
            ]));
        }
        
        const pagination = new Pagination(
            document.querySelector('.pagination'),
            tag('li', {'class': 'page-item'}, [
                tag('a', {'href': '#', 'class': 'slot page-link'}, [])
            ]),
            data.count,
            this.read.bind(this)
        );
        pagination.init();
    }
}

window.addEventListener('load', e => {
    new Page(
        document.getElementById('btn-add'),
        document.getElementById('table'),
        document.getElementById('form')
    ).init()
})