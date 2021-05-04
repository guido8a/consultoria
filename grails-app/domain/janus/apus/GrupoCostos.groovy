package janus.apus

import janus.Item

class GrupoCostos implements Serializable {

    String descripcion
    int orden = 0

    static auditable = true
    static mapping = {
        table 'grcs'
        cache usage: 'read-write', include: 'non-lazy'
        id column: 'grcs__id'
        id generator: 'identity'
        version false
        columns {
            id column: 'grcs__id'
            descripcion column: 'grcsdscr'
            orden column: 'grcsordn'
        }
    }
    static constraints = {
        descripcion(size: 1..60, blank: false, nullable: false, attributes: [title: 'descrición'])
        orden(blank: false, nullable: false, attributes: [title: 'orden'])
    }
    String toString(){
        "${this.descripcion}"
    }
}